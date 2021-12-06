package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.Route

interface RoutesRepository {

    suspend fun getAllSetRoutes(): List<Route>

    suspend fun getRouteById(routeId: Long) : Route?
}
