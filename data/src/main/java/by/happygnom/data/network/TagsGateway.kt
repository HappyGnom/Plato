package by.happygnom.data.network

import by.happygnom.data.BuildConfig
import by.happygnom.data.model.TagEntity
import io.ktor.client.*
import io.ktor.client.request.*

class TagsGateway(private val client: HttpClient) {

    suspend fun getAllTags(): List<TagEntity> {
        return client.get("${BuildConfig.API_ADRESS}/Tags")
    }
}
