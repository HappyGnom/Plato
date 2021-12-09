package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.NewsEntity
import by.happygnom.data.model.api_specific.ApiComment
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class NewsGateway(private val client: HttpClient) {

    suspend fun getNews(count: Long): List<NewsEntity> {
        return client.get("${BuildConfig.API_ADRESS}/News?count=$count")
    }

//    suspend fun publishComment(routeId: Long, userId: Long, message: String) {
//        return client.post("${BuildConfig.API_ADRESS}/Route/$routeId/Comment?userId=$userId") {
//            contentType(ContentType.Application.Json)
//            this.body = message
//        }
//    }
}
