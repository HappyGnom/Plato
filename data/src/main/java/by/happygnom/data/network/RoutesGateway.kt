package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.api_specific.ApiRoute
import by.happygnom.data.model.requests.CreateRouteRequestImpl
import by.happygnom.data.model.requests.UpdateRouteRequestImpl
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class RoutesGateway(private val client: HttpClient) {

    suspend fun getAllRoutes(): List<ApiRoute> {
        return client.get("${BuildConfig.API_ADRESS}/Route")
    }

    suspend fun getRouteById(routeId: Long): ApiRoute {
        return client.get("${BuildConfig.API_ADRESS}/Route/$routeId")
    }

    suspend fun createRoute(request: CreateRouteRequestImpl): HttpResponse {
        return client.post("${BuildConfig.API_ADRESS}/Route") {
            body = request
        }
    }

    suspend fun updateRoute(request: UpdateRouteRequestImpl): HttpResponse {
        return client.put("${BuildConfig.API_ADRESS}/Route/${request.routeId}") {
            body = request
        }
    }

    suspend fun takeDownRoute(routeId: Long): HttpResponse {
        return client.post("${BuildConfig.API_ADRESS}/Route/${routeId}/TakeDown")
    }

    suspend fun setRouteLike(routeId: Long, isLiked: Boolean): HttpResponse {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Like"
        return if (isLiked)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }

    suspend fun setRouteSent(routeId: Long, isSent: Boolean): HttpResponse {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Send"
        return if (isSent)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }

    suspend fun setRouteBookmark(routeId: Long, isBookmarked: Boolean): HttpResponse {
        val requestUrl = "${BuildConfig.API_ADRESS}/Route/$routeId/Bookmark"
        return if (isBookmarked)
            client.post(requestUrl)
        else
            client.delete(requestUrl)
    }
}
