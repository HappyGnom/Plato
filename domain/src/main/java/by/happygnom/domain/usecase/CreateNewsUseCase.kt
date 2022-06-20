package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.model.CreateNewsRequest
import by.happygnom.domain.data_interface.model.CreateRouteRequest
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository

class CreateNewsUseCase(private val newsRepository: NewsRepository) :
    UseCase<Unit>("CreateNewsUseCase") {

    lateinit var inputCreateNewsRequest: CreateNewsRequest

    override suspend fun performTask() {
        return newsRepository.createNews(inputCreateNewsRequest)
    }
}
