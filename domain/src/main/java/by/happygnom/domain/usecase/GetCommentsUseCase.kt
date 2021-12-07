package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.model.Comment
import by.happygnom.domain.model.Route

class GetCommentsUseCase(private val commentsRepository: CommentsRepository) :
    UseCase<List<Comment>>("GetCommentsUseCase") {

    var inputRouteId: Long = 0
    var inputCount: Long = 0

    override suspend fun performTask(): List<Comment> {
        return commentsRepository.getComments(inputRouteId, inputCount)
    }
}
