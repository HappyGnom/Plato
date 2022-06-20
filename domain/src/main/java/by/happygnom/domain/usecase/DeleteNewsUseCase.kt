package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.NewsRepository

class DeleteNewsUseCase(private val newsRepository: NewsRepository) :
    UseCase<Unit>("DeleteNewsUseCase") {

    var inputNewsId: Long = -1

    override suspend fun performTask() {
        return newsRepository.deleteNews(inputNewsId)
    }
}
