package by.happygnom.plato.ui.screens.news.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.model.News
import by.happygnom.domain.usecase.GetNewsByIdUseCase
import by.happygnom.plato.navigation.ArgNames
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val newsId = savedState.get<Long>(ArgNames.NEWS_ID) ?: -1

    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadNews()
    }

    fun loadNews(forceUpdate: Boolean = false) {
        val getNewsByIdUseCase = GetNewsByIdUseCase(newsRepository)
        getNewsByIdUseCase.inputNewsId = newsId
        getNewsByIdUseCase.inputForceUpdate = forceUpdate
        _isLoading.value = true

        getNewsByIdUseCase.executeAsync {
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
