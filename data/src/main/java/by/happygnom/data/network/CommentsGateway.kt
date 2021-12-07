package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.CommentEntity
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class CommentsGateway(private val client: HttpClient) {

    suspend fun getComments(routeId: Long, count: Long): List<CommentEntity> {
        return client.get("${BuildConfig.API_ADRESS}/Route/$routeId/Comments?count=$count")
    }

    suspend fun publishComment(routeId: Long, userId: Long, message: String) {
        return client.post("${BuildConfig.API_ADRESS}/Route/$routeId/Comment?userId=$userId") {
            contentType(ContentType.Application.Json)
            this.body = message
        }
    }
}
