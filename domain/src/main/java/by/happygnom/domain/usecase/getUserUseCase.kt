package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) :
    UseCase<Unit>("GetUserUseCase") {

    var id: String? = null

    override suspend fun performTask() {
        userRepository.getUser(id!!)
    }
}