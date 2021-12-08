package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.data_interface.model.CreateRouteRequest
import by.happygnom.domain.data_interface.model.UpdateRouteRequest
import by.happygnom.domain.model.Route

interface RoutesRepository {

    suspend fun createRoute(request: CreateRouteRequest)

    suspend fun updateRoute(request: UpdateRouteRequest)

    suspend fun getAllRoutes(forceUpdate: Boolean = false): List<Route>

    suspend fun getRouteById(routeId: Long, forceUpdate: Boolean = false): Route?

    suspend fun updateAllRoutes()

    suspend fun setRouteLike(routeId: Long, isLiked: Boolean)

    suspend fun getIsRouteLiked(routeId: Long) : Boolean

    suspend fun setRouteSent(routeId: Long, isSent: Boolean)

    suspend fun getIsRouteSent(routeId: Long) : Boolean

    suspend fun setRouteBookmark(routeId: Long, isBookmarked: Boolean)

    suspend fun getIsRouteBookmarked(routeId: Long) : Boolean
}
