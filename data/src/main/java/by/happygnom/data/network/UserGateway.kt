package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.UserEntity
import by.happygnom.data.model.api_specific.ApiUser
import io.ktor.client.*
import io.ktor.client.request.*

class UserGateway(private val client: HttpClient) {

    suspend fun registerUser(user: ApiUser) {
        return client.post("${BuildConfig.API_ADRESS}/User/Register"){
            body = user
        }
    }

    suspend fun getUserById(id: String): ApiUser {
        return client.get("${BuildConfig.API_ADRESS}/User/$id?fetchRoutes=true")
    }
}
