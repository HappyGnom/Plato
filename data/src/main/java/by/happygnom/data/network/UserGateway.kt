package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.UserEntity
import by.happygnom.domain.model.User
import io.ktor.client.*
import io.ktor.client.request.*

class UserGateway(private val client: HttpClient) {

//    suspend fun getAllUsers(): List<UserEntity> {
//        return client.get("${BuildConfig.API_ADRESS}/User")
//    }

    suspend fun registerUser(user: UserEntity) {
        return client.get("${BuildConfig.API_ADRESS}/User/Register?user=$user")
    }

    suspend fun getUserById(idToken: String): UserEntity {
        return client.get("${BuildConfig.API_ADRESS}/User/$idToken")
    }

}