package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository

class SetRouteSentUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("SetRouteSentUseCase") {

    var inputRouteId: Long = 0
    var isSent: Boolean = false

    override suspend fun performTask() {
        routesRepository.setRouteSent(inputRouteId, isSent)
    }
}
