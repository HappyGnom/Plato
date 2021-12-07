package by.happygnom.plato.ui.screens.routes.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.model.Comment
import by.happygnom.domain.usecase.GetCommentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val commentsRepository: CommentsRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val routeId = savedState.get<Long>("route_id") ?: -1

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadComments()
    }

    fun loadComments() {
        val getCommentsUseCase = GetCommentsUseCase(commentsRepository)
        getCommentsUseCase.inputRouteId = routeId
        getCommentsUseCase.inputCount = 50
        _isLoading.value = true

        getCommentsUseCase.executeAsync {
            onSuccess {
                _comments.postValue(it)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }
}
