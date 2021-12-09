package by.happygnom.data.repository

import by.happygnom.data.dao.NewsDao
import by.happygnom.data.network.NewsGateway
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.model.News

class NewsRepositoryImpl(
    private val newsGateway: NewsGateway,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun getNews(forceUpdate: Boolean): List<News> {
        return newsDao.ensureIsNotEmpty(forceUpdate).getAll().map { it.toDomain() }
    }

    override suspend fun getNewsById(newsId: Long, forceUpdate: Boolean): News? {
        return newsDao.ensureIsNotEmpty(forceUpdate).getById(newsId)?.toDomain()
    }

    private suspend fun NewsDao.ensureIsNotEmpty(forceUpdate: Boolean = false) = apply {
        if (this.count() == 0L || forceUpdate) {
            updateAllNews()
        }
    }

    override suspend fun updateAllNews() {
        newsDao.insertNews(newsGateway.getNews(50))
    }
}
