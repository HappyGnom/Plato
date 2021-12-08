package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.RoutesFilter

class GetRoutesUseCase(private val routesRepository: RoutesRepository) :
    UseCase<List<Route>>("GetRoutesUseCase") {

    var inputForceUpdate: Boolean = false
    lateinit var inputRoutesFilter: RoutesFilter

    override suspend fun performTask(): List<Route> {
        return routesRepository.getFilteredRoutes(inputRoutesFilter, inputForceUpdate)
    }
}
