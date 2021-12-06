package by.happygnom.plato.ui.screens.routes.add_comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor() : ViewModel() {

    private val _commentContent = MutableLiveData("")
    val commentContent: LiveData<String> = _commentContent

    fun setCommentContent(content: String) {
        _commentContent.value = content
    }

    fun send(){

    }
}
