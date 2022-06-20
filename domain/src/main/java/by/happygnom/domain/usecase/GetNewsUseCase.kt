package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.model.News

class GetNewsUseCase(private val newsRepository: NewsRepository) :
    UseCase<List<News>>("GetNewsUseCase") {

    //var inputCount: Long = 0
    var inputForceUpdate: Boolean = false
    var inputOnlyPublished: Boolean = true

    override suspend fun performTask(): List<News> {
        return newsRepository.getAllNews(inputForceUpdate, inputOnlyPublished)
    }
}
