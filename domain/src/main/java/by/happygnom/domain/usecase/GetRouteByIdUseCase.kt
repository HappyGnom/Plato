package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route

class GetRouteByIdUseCase(private val routesRepository: RoutesRepository) :
    UseCase<Route>("GetRouteByIdUseCase") {

    var inputRouteId: Long = 0

    override suspend fun performTask(): Route {
        return routesRepository.getRouteById(inputRouteId)
            ?: throw NullPointerException("Route with id $inputRouteId not found")
    }
}
