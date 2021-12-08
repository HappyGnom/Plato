package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User

class UpdateUserUseCase(private val userRepository: UserRepository) :
    UseCase<Unit>("UpdateUserUseCase") {

    var user: User? = null

    override suspend fun performTask() {
        userRepository.updateUser(user!!)
    }
}