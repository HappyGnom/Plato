package by.happygnom.data.repository

import by.happygnom.data.dao.RouteInteractionsDao
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.model.RouteBookmarkEntity
import by.happygnom.data.model.RouteEntity
import by.happygnom.data.model.RouteLikeEntity
import by.happygnom.data.model.RouteSentEntity
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef
import by.happygnom.data.model.requests.CreateRouteRequestImpl
import by.happygnom.data.model.requests.UpdateRouteRequestImpl
import by.happygnom.data.network.RoutesGateway
import by.happygnom.domain.data_interface.model.CreateRouteRequest
import by.happygnom.domain.data_interface.model.UpdateRouteRequest
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route

class RoutesRepositoryImpl(
    private val routesGateway: RoutesGateway,
    private val routesDao: RoutesDao,
    private val routeInteractionsDao: RouteInteractionsDao
) : RoutesRepository {

    override suspend fun createRoute(request: CreateRouteRequest) {
        routesGateway.createRoute(request as CreateRouteRequestImpl)
    }

    override suspend fun updateRoute(request: UpdateRouteRequest) {
        routesGateway.updateRoute(request as UpdateRouteRequestImpl)
    }

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
        val apiRoutes = routesGateway.getAllRoutes()
        val routeEntities = mutableListOf<RouteEntity>()
        val routeTagRefs = mutableListOf<RouteTagsCrossRef>()

        apiRoutes.forEach { route ->
            val (routeEntity, tagRefs) = route.toEntity()

            routeEntities.add(routeEntity)
            routeTagRefs.addAll(tagRefs)
        }

        routesDao.insertRoutes(routeEntities)
        routesDao.insertRouteTagCrossRefs(routeTagRefs)
    }

    override suspend fun setRouteLike(routeId: Long, isLiked: Boolean) {
        val result = routesGateway.setRouteLike(routeId, isLiked)

        if (result.status.value == 200)
            routeInteractionsDao.setLike(RouteLikeEntity(routeId))
    }

    override suspend fun getIsRouteLiked(routeId: Long): Boolean {
        return routeInteractionsDao.isLiked(routeId)
    }

    override suspend fun setRouteSent(routeId: Long, isSent: Boolean) {
        val result = routesGateway.setRouteSent(routeId, isSent)

        if (result.status.value == 200)
            routeInteractionsDao.setSent(RouteSentEntity(routeId))
    }

    override suspend fun getIsRouteSent(routeId: Long): Boolean {
        return routeInteractionsDao.isSent(routeId)
    }

    override suspend fun setRouteBookmark(routeId: Long, isBookmarked: Boolean) {
        val result = routesGateway.setRouteBookmark(routeId, isBookmarked)

        if (result.status.value == 200)
            routeInteractionsDao.setBookmark(RouteBookmarkEntity(routeId))
    }

    override suspend fun getIsRouteBookmarked(routeId: Long): Boolean {
        return routeInteractionsDao.isBookmarked(routeId)
    }
}
