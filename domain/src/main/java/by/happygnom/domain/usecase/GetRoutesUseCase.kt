package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route

class GetRoutesUseCase(private val routesRepository: RoutesRepository) :
    UseCase<List<Route>>("GetRoutesUseCase") {

    var inputForceUpdate: Boolean = false

    override suspend fun performTask(): List<Route> {
        return routesRepository.getAllRoutes(inputForceUpdate)
    }
}