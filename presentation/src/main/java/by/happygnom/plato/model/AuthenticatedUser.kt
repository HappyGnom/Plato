package by.happygnom.plato.model

import by.happygnom.data.repository.UserRepositoryImpl
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User
import by.happygnom.domain.usecase.GetUserUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthenticatedUser {
    private var user: User? = null

    fun get() : User? {
        return user
    }

    fun defineUser(userRepository: UserRepository, forceUpdate: Boolean = false) {
        val getUserUseCase = GetUserUseCase(userRepository, forceUpdate)
        getUserUseCase.inputFirebaseUid = Firebase.auth.currentUser?.uid
        getUserUseCase.executeAsync {
            onSuccess {
                user = it
            }
        }
    }
}