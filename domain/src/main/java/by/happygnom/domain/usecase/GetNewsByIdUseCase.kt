package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.News
import by.happygnom.domain.model.Route

class GetNewsByIdUseCase(private val newsRepository: NewsRepository) :
    UseCase<News>("GetNewsByIdUseCase") {

    var inputNewsId: Long = 0
    var inputForceUpdate: Boolean = false

    override suspend fun performTask(): News {
        return newsRepository.getNewsById(inputNewsId, inputForceUpdate)
            ?: throw NullPointerException("Route with id $inputNewsId not found")
    }
}
