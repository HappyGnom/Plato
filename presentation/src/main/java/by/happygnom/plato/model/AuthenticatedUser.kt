package by.happygnom.plato.model

import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User
import by.happygnom.domain.usecase.GetUserUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthenticatedUser {

    private var user: User? = null
    var isAdmin = false
        private set

    fun get(): User? {
        return user
    }

    fun defineUser(userRepository: UserRepository, forceUpdate: Boolean = false) {
        val currentFirebaseUser = Firebase.auth.currentUser

        val getUserUseCase = GetUserUseCase(userRepository, forceUpdate)
        getUserUseCase.inputFirebaseUid = currentFirebaseUser?.uid
        getUserUseCase.executeAsync {
            onSuccess {
                user = it
            }
        }

        currentFirebaseUser?.getIdToken(true)?.addOnSuccessListener {
            val roleClaim =
                it.claims["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]

            if (roleClaim == null) {
                isAdmin = false
                return@addOnSuccessListener
            }

            if (roleClaim == "admin" || roleClaim == "superuser") {
                isAdmin = true
                return@addOnSuccessListener
            }
        }
    }
}
