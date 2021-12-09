package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository

class TakeDownRouteUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("TakeDownRouteUseCase") {

    var inputRouteId: Long = -1

    override suspend fun performTask() {
        return routesRepository.takeDownRoute(inputRouteId)
    }
}
