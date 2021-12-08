package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.model.CreateRouteRequest
import by.happygnom.domain.data_interface.model.UpdateRouteRequest
import by.happygnom.domain.data_interface.repository.RoutesRepository

class UpdateRouteUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("UpdateRouteUseCase") {

    lateinit var inputUpdateRouteRequest: UpdateRouteRequest

    override suspend fun performTask() {
        return routesRepository.updateRoute(inputUpdateRouteRequest)
    }
}
