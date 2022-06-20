package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.data_interface.model.CreateNewsRequest
import by.happygnom.domain.data_interface.model.UpdateNewsRequest
import by.happygnom.domain.model.News

interface NewsRepository {

    suspend fun createNews(request: CreateNewsRequest)

    suspend fun updateNews(request: UpdateNewsRequest)

    suspend fun deleteNews(newsId: Long)

    suspend fun getAllNews(forceUpdate: Boolean = false, onlyPublished: Boolean = true): List<News>

    suspend fun getNewsById(newsId: Long, forceUpdate: Boolean = false): News?

    suspend fun updateAllNews(onlyPublished: Boolean = true)
}
