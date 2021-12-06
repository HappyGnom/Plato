package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.RouteEntity
import io.ktor.client.*
import io.ktor.client.request.*

class RoutesGateway(private val client: HttpClient) {

    suspend fun getSetRoutes(): List<RouteEntity> {
        return client.get("${BuildConfig.API_ADRESS}/Route")
    }

    suspend fun getRouteById(routeId: Long): RouteEntity {
        return client.get("${BuildConfig.API_ADRESS}/Route/$routeId")
    }
}
