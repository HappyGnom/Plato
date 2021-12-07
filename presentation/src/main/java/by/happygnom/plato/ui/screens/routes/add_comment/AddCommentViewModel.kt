package by.happygnom.plato.ui.screens.routes.add_comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.usecase.PublishCommentUseCase
import by.happygnom.plato.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    private val commentsRepository: CommentsRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val routeId = savedState.get<Long>("route_id") ?: -1

    private val _commentContent = MutableLiveData("")
    val commentContent: LiveData<String> = _commentContent

    private val _commentPublished = MutableLiveData<Event<Boolean>>()
    val commentPublished : LiveData<Event<Boolean>> = _commentPublished

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setCommentContent(content: String) {
        _commentContent.value = content
    }

    fun publishComment() {
        val publishCommentUseCase = PublishCommentUseCase(commentsRepository)
        publishCommentUseCase.inputMessage = commentContent.value!!
        publishCommentUseCase.inputRouteId = routeId
        publishCommentUseCase.inputUserId = 134
        _isLoading.value = true

        publishCommentUseCase.executeAsync {
            onSuccess {
                _commentPublished.value = Event(true)
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
