package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.Comment
import by.happygnom.domain.model.Route

interface CommentsRepository {

    suspend fun getComments(routeId: Long, count: Long): List<Comment>

    suspend fun publishComment(routeId: Long, userId: Long, message: String)
}
