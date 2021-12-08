package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository

class SetRouteLikeUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("SetRouteLikeUseCase") {

    var inputRouteId: Long = 0
    var isLiked: Boolean = false

    override suspend fun performTask() {
        routesRepository.setRouteLike(inputRouteId, isLiked)
    }
}
