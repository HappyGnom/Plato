package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User

class RegisterUseCase(private val userRepository: UserRepository) :
    UseCase<Unit>("RegisterUserUseCase") {

    var user: User? = null

    override suspend fun performTask() {
        userRepository.registerUser(user!!)
    }
}