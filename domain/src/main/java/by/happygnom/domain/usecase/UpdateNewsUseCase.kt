package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.model.UpdateNewsRequest
import by.happygnom.domain.data_interface.repository.NewsRepository

class UpdateNewsUseCase(private val newsRepository: NewsRepository) :
    UseCase<Unit>("UpdateNewsUseCase") {

    lateinit var inputUpdateNewsRequest: UpdateNewsRequest

    override suspend fun performTask() {
        return newsRepository.updateNews(inputUpdateNewsRequest)
    }
}
