package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.CommentsRepository

class PublishCommentUseCase(private val commentsRepository: CommentsRepository) :
    UseCase<Unit>("PublishCommentUseCase") {

    var inputRouteId: Long = 0
    var inputUserId: Long = 0
    lateinit var inputMessage: String

    override suspend fun performTask() {
        commentsRepository.publishComment(inputRouteId, inputUserId, inputMessage)
    }
}
