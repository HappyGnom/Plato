package by.happygnom.plato.ui.screens.news.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.model.News
import by.happygnom.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<News>>(listOf())
    val news: LiveData<List<News>> = _news

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadNews()
    }

    fun loadNews(forceUpdate: Boolean = false) {
        val getNewsUseCase = GetNewsUseCase(newsRepository)
        getNewsUseCase.inputForceUpdate = forceUpdate
        _isLoading.value = true

        getNewsUseCase.executeAsync {
            onSuccess {
                _news.postValue(it)
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
