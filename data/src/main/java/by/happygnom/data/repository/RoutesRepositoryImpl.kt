package by.happygnom.data.repository

import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.network.RoutesGateway
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route

class RoutesRepositoryImpl(
    private val routesGateway: RoutesGateway,
    private val routesDao: RoutesDao
) : RoutesRepository {

    override suspend fun getAllRoutes(forceUpdate: Boolean): List<Route> {
        return routesDao.ensureIsNotEmpty(forceUpdate).getAll().map { it.toDomain() }
    }

    override suspend fun getRouteById(routeId: Long, forceUpdate: Boolean): Route? {
        return routesDao.ensureIsNotEmpty(forceUpdate).getById(routeId)?.toDomain()
    }

    private suspend fun RoutesDao.ensureIsNotEmpty(forceUpdate: Boolean = false) = apply {
        if (this.count() == 0L || forceUpdate) {
            updateAllRoutes()
        }
    }

    override suspend fun updateAllRoutes() {
        val routes = routesGateway.getAllRoutes()
        routesDao.insert(routes)
    }

    override suspend fun setRouteLike(routeId: Long, userId: Long, isLiked: Boolean) {
        val result = routesGateway.setRouteLike(routeId, userId, isLiked)
        //TODO Update current user object with like to that route
    }

    override suspend fun setRouteSent(routeId: Long, userId: Long, isSent: Boolean) {
        val result = routesGateway.setRouteSent(routeId, userId, isSent)
        //TODO Update current user object with sent status to that route
    }

    override suspend fun setRouteBookmark(routeId: Long, userId: Long, isBookmarked: Boolean) {
        val result = routesGateway.setRouteBookmark(routeId, userId, isBookmarked)
        //TODO Update current user object with bookmark to that route
    }
}
