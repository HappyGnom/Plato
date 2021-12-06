package by.happygnom.domain.usecase

import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

internal typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<T> constructor(
    private val tag: String,
    private val foregroundContext: CoroutineContext = Dispatchers.Main,
    private val backgroundContext: CoroutineContext = Dispatchers.IO,
) {

    @Suppress("PropertyName")
    private var parentJob: Job = Job()

    fun executeSync(): T {
        try {
            val result: T
            val executionMillis = measureTimeMillis {
                result = runBlocking {
                    performTask()
                }
            }

            Log.d(tag, "Execution success (${executionMillis}ms.)")
            return result
        } catch (e: Exception) {
            Log.e(tag, "Execution error", e)
            throw e
        }
    }

    fun executeAsync(block: CompletionBlock<T>) {
        Log.d(tag, "Execution started")

        val response = Request<T>().apply { block() }
        unsubscribe()

        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result: T
                val executionMillis = measureTimeMillis {
                    result = withContext(backgroundContext) { performTask() }
                }

                Log.d(tag, "Execution success (${executionMillis}ms.)")
                response(result)
            } catch (cancellationException: CancellationException) {
                Log.e(tag, "Cancellation exception", cancellationException)
                response(cancellationException)
            } catch (e: Exception) {
                Log.e(tag, "Execution error", e)
                response(e)
            }
        }
    }

    protected abstract suspend fun performTask(): T

    @Suppress("MemberVisibilityCanBePrivate")
    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onSuccess: ((T) -> Unit)? = null
        private var onFailure: ((Exception) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null
        private var onComplete: (() -> Unit)? = null

        fun onSuccess(block: (T) -> Unit) {
            onSuccess = block
        }

        fun onFailure(block: (Exception) -> Unit) {
            onFailure = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }

        fun onComplete(block: () -> Unit) {
            onComplete = block
        }

        operator fun invoke(result: T) {
            onSuccess?.invoke(result)
            onComplete?.invoke()
        }

        operator fun invoke(error: Exception) {
            onFailure?.invoke(error)
            onComplete?.invoke()
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }
    }
}
