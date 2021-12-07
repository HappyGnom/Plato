package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.Route

interface RoutesRepository {

    suspend fun getAllRoutes(forceUpdate: Boolean = false): List<Route>

    suspend fun getRouteById(routeId: Long, forceUpdate: Boolean = false): Route?

    suspend fun updateAllRoutes()

    suspend fun setRouteLike(routeId: Long, userId: Long, isLiked: Boolean)

    suspend fun setRouteSent(routeId: Long, userId: Long, isSent: Boolean)

    suspend fun setRouteBookmark(routeId: Long, userId: Long, isBookmarked: Boolean)
}
