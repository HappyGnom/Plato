package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.model.CreateRouteRequest
import by.happygnom.domain.data_interface.repository.RoutesRepository

class CreateRouteUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Unit>("CreateRouteUseCase") {

    lateinit var inputCreateRouteRequest: CreateRouteRequest

    override suspend fun performTask() {
        return routesRepository.createRoute(inputCreateRouteRequest)
    }
}
