package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.RoutesRepository

class GetRoutesettersUseCase(private val routesRepository: RoutesRepository) :
    UseCase<List<String>>("GetRoutesettersUseCase") {

    var inputForceUpdate: Boolean = false

    override suspend fun performTask(): List<String> {
        return routesRepository.getRoutesetters()
    }
}
