package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.News

interface NewsRepository {

    suspend fun getNews(forceUpdate: Boolean = false): List<News>

    suspend fun getNewsById(newsId: Long, forceUpdate: Boolean = false): News?

    suspend fun updateAllNews()
}
