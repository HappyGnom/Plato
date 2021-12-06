package by.happygnom.data.repository

import by.happygnom.data.network.RoutesGateway
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route

class RoutesRepositoryImpl(
    private val routesGateway: RoutesGateway,
//    private val routesDB: RoutesDB
) : RoutesRepository {

    override suspend fun getAllSetRoutes(): List<Route> {
        return routesGateway.getSetRoutes().map { it.toDomain() }.sortedByDescending { it.setDate }
    }

    override suspend fun getRouteById(routeId: Long): Route {
        return routesGateway.getRouteById(routeId).toDomain()
    }
}
