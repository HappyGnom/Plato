package by.happygnom.plato.ui.screens.news.news_editor

import android.graphics.drawable.BitmapDrawable
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.data.model.requests.CreateNewsRequestImpl
import by.happygnom.data.model.requests.UpdateNewsRequestImpl
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.usecase.CreateNewsUseCase
import by.happygnom.domain.usecase.DeleteNewsUseCase
import by.happygnom.domain.usecase.GetNewsByIdUseCase
import by.happygnom.domain.usecase.UpdateNewsUseCase
import by.happygnom.plato.model.InputValidator
import by.happygnom.plato.navigation.ArgNames
import by.happygnom.plato.util.Event
import by.happygnom.plato.util.toBase64
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewsEditorViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val imageLoader: ImageLoader,
    private val imageRequestBuilder: ImageRequest.Builder,
    savedState: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDone = MutableLiveData<Event<Boolean>>()
    val isDone: LiveData<Event<Boolean>> = _isDone

    private val _existingNewsId = MutableLiveData<Long?>(null)
    val existingNewsId: LiveData<Long?> = _existingNewsId

    private val _pictureBase64 = MutableLiveData<String?>(null)
    val pictureBase64: LiveData<String?> = _pictureBase64

    private val _pictureUrl = MutableLiveData<String?>(null)
    val pictureUrl: LiveData<String?> = _pictureUrl

    private val _isPictureUpdated = MutableLiveData<Boolean>(false)
    val isPictureUpdated: LiveData<Boolean> = _isPictureUpdated

    private val _header = MutableLiveData("")
    val header: LiveData<String> = _header

    private val _text = MutableLiveData("")
    val text: LiveData<String> = _text

    private val _publishDate = MutableLiveData<Date?>(null)
    val publishDate: LiveData<Date?> = _publishDate

    private val _publishTime = MutableLiveData<Date?>(null)
    val publishTime: LiveData<Date?> = _publishTime

    private val _errors = MutableLiveData<NewsEditorErrors?>(null)
    val errors: LiveData<NewsEditorErrors?> = _errors

    init {
        val newsId = savedState.get<Long>(ArgNames.EXISTING_NEWS_ID)
        if (newsId != null && newsId >= 0)
            loadNews(newsId)
    }

    private fun loadNews(newsId: Long) {
        val getNewsByIdUseCase = GetNewsByIdUseCase(newsRepository)
        getNewsByIdUseCase.inputNewsId = newsId
        _isLoading.value = true

        getNewsByIdUseCase.executeAsync {
            onSuccess { news ->
                _existingNewsId.value = news.id
                _pictureUrl.value = news.pictureUrl
                setPicture(news.pictureUrl)
                _header.value = news.header
                _text.value = news.text
                _publishDate.value = news.publishDateTime
                _publishTime.value = news.publishDateTime
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    fun setPicture(pictureUri: String) {
        val imageRequest = imageRequestBuilder
            .data(pictureUri)
            .target { result ->
                val base64 = (result as BitmapDrawable?)?.bitmap?.toBase64()
                _pictureBase64.value = base64
            }
            .build()

        imageLoader.enqueue(imageRequest)
    }

    fun setPictureUpdated(value: Boolean) {
        _isPictureUpdated.value = value
    }

    fun setHeader(header: String) {
        _header.value = header
    }

    fun setText(text: String) {
        _text.value = text
    }

    fun setPublishDate(publishDate: Date?) {
        _publishDate.value = publishDate
    }

    fun setPublishTime(publishTime: Date?) {
        _publishTime.value = publishTime
    }

    fun deleteNews() {
        val deleteNewsUseCase = DeleteNewsUseCase(newsRepository)
        deleteNewsUseCase.inputNewsId = existingNewsId.value!!

        _isLoading.value = true

        deleteNewsUseCase.executeAsync {
            onSuccess {
                _isDone.value = Event(true)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    fun saveNews() {
        val validInput = validateInput()
        if (!validInput) return

        if (existingNewsId.value == null)
            createNews()
        else
            updateNews()
    }

    private fun createNews() {
        val createNewsUseCase = CreateNewsUseCase(newsRepository)

        val dateTime = Calendar.getInstance()
        dateTime.time = publishDate.value!!
        val time = Calendar.getInstance()
        time.time = publishTime.value!!

        dateTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY))
        dateTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE))

        createNewsUseCase.inputCreateNewsRequest = CreateNewsRequestImpl(
            header.value!!,
            text.value!!,
            dateTime.time.time / DateUtils.SECOND_IN_MILLIS,
            null,
            pictureBase64.value!!
        )
        _isLoading.value = true

        createNewsUseCase.executeAsync {
            onSuccess {
                _isDone.value = Event(true)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    private fun updateNews() {
        val updateNewsUseCase = UpdateNewsUseCase(newsRepository)

        val dateTime = Calendar.getInstance()
        dateTime.time = publishDate.value!!
        val time = Calendar.getInstance()
        time.time = publishTime.value!!

        dateTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY))
        dateTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE))

        val newPictureUrl = if (!isPictureUpdated.value!!) null
        else _pictureUrl.value

        updateNewsUseCase.inputUpdateNewsRequest = UpdateNewsRequestImpl(
            existingNewsId.value!!,
            header.value!!,
            text.value!!,
            dateTime.time.time / DateUtils.SECOND_IN_MILLIS,
            newPictureUrl,
            pictureBase64.value!!
        )
        _isLoading.value = true

        updateNewsUseCase.executeAsync {
            onSuccess {
                _isDone.value = Event(true)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    private fun validateInput(): Boolean {
        val errors = getInputErrorsOrNull()
        _errors.value = getInputErrorsOrNull()

        return errors == null
    }

    private fun getInputErrorsOrNull(): NewsEditorErrors? {
        val pictureErrorId = InputValidator.getPictureBase64ErrorIdOrNull(pictureBase64.value)
        val headerErrorId = InputValidator.getNewsHeaderErrorIdOrNull(header.value ?: "")
        val textErrorId = InputValidator.getNewsTextErrorIdOrNull(text.value ?: "")
        val publishDateErrorId = InputValidator.getNewsDateErrorIdOrNull(publishDate.value)
        val publishTimeErrorId = InputValidator.getNewsTimeErrorIdOrNull(publishTime.value)

        val errors = NewsEditorErrors(
            pictureErrorId,
            headerErrorId,
            textErrorId,
            publishDateErrorId,
            publishTimeErrorId
        )

        return if (listOf(
                pictureErrorId, headerErrorId,
                textErrorId, publishDateErrorId,
                publishTimeErrorId
            ).all { it == null }
        )
            null
        else
            errors
    }
}
