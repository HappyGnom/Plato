package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository

class SetRouteBookmarkUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("SetRouteBookmarkUseCase") {

    var inputRouteId: Long = 0
    var inputUserId: Long = 0
    var isBookmarked: Boolean = false

    override suspend fun performTask() {
        routesRepository.setRouteBookmark(inputRouteId, inputUserId, isBookmarked)
    }
}
