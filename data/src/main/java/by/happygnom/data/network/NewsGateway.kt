package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.NewsEntity
import by.happygnom.data.model.api_specific.ApiRoute
import by.happygnom.data.model.requests.CreateNewsRequestImpl
import by.happygnom.data.model.requests.CreateRouteRequestImpl
import by.happygnom.data.model.requests.UpdateNewsRequestImpl
import by.happygnom.data.model.requests.UpdateRouteRequestImpl
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class NewsGateway(private val client: HttpClient) {

    suspend fun getAllPublishedNews(count: Long): List<NewsEntity> {
        return client.get("${BuildConfig.API_ADRESS}/News/Published?count=$count")
    }

    suspend fun getAllNews(count: Long): List<NewsEntity> {
        return client.get("${BuildConfig.API_ADRESS}/News?count=$count")
    }

    suspend fun getNewsById(newsId: Long): NewsEntity {
        return client.get("${BuildConfig.API_ADRESS}/News/$newsId")
    }

    suspend fun createNews(request: CreateNewsRequestImpl): HttpResponse {
        return client.post("${BuildConfig.API_ADRESS}/News") {
            body = request
        }
    }

    suspend fun updateNews(request: UpdateNewsRequestImpl): HttpResponse {
        return client.put("${BuildConfig.API_ADRESS}/News/${request.newsId}") {
            body = request
        }
    }

    suspend fun deleteNews(newsId: Long): HttpResponse {
        return client.delete("${BuildConfig.API_ADRESS}/News/${newsId}")
    }

}
