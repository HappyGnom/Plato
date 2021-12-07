package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.RouteEntity
import io.ktor.client.*
import io.ktor.client.request.*

class RoutesGateway(private val client: HttpClient) {

    suspend fun getAllRoutes(): List<RouteEntity> {
        return client.get("${BuildConfig.API_ADRESS}/Route")
    }

    suspend fun getRouteById(routeId: Long): RouteEntity {
        return client.get("${BuildConfig.API_ADRESS}/Route/$routeId")
    }

    suspend fun setRouteLike(routeId: Long, userId: Long, isLiked: Boolean) {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Like?userId=$userId"
        return if (isLiked)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }

    suspend fun setRouteSent(routeId: Long, userId: Long, isSent: Boolean) {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Send?userId=$userId"
        return if (isSent)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }

    suspend fun setRouteBookmark(routeId: Long, userId: Long, isBookmarked: Boolean) {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Bookmark?userId=$userId"
        return if (isBookmarked)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }
}
