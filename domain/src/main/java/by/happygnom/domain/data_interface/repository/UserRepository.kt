package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.User

interface UserRepository {
    suspend fun registerUser(user: User)

    suspend fun getUser(id: String): User?
}