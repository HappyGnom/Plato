package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.RouteInteractions

class GetRouteInteractionsUseCase(private val routesRepository: RoutesRepository) :
    UseCase<RouteInteractions>("SetRouteSentUseCase") {

    var inputRouteId: Long = 0

    override suspend fun performTask(): RouteInteractions {
        val isLiked = routesRepository.getIsRouteLiked(inputRouteId)
        val isBookmarked = routesRepository.getIsRouteBookmarked(inputRouteId)
        val isSent = routesRepository.getIsRouteSent(inputRouteId)

        return RouteInteractions(isLiked, isBookmarked, isSent)
    }
}
