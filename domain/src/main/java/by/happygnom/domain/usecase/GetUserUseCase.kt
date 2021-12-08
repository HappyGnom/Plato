package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User

class GetUserUseCase(private val userRepository: UserRepository, private val forceUpdate: Boolean = false) :
    UseCase<User?>("GetUserUseCase") {

    var inputFirebaseUid: String? = null

    override suspend fun performTask(): User? {
        return userRepository.getUser(inputFirebaseUid!!, forceUpdate)
    }
}
