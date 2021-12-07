package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(forceUpdate: Boolean = false): List<User>

    suspend fun getUserByIdToken(idToken: String, forceUpdate: Boolean = false): User?
}