package by.happygnom.data.repository

import by.happygnom.data.network.CommentsGateway
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.model.Comment

class CommentsRepositoryImpl(
    private val commentsGateway: CommentsGateway
) : CommentsRepository {

    override suspend fun getComments(routeId: Long, count: Long): List<Comment> {
        return commentsGateway.getComments(routeId, count).map { it.toDomain() }
    }

    override suspend fun publishComment(routeId: Long, userId: Long, message: String) {
        return commentsGateway.publishComment(routeId, userId, message)
    }
}
