package by.happygnom.data.repository

import by.happygnom.data.dao.NewsDao
import by.happygnom.data.model.requests.CreateNewsRequestImpl
import by.happygnom.data.model.requests.UpdateNewsRequestImpl
import by.happygnom.data.network.NewsGateway
import by.happygnom.domain.data_interface.model.CreateNewsRequest
import by.happygnom.domain.data_interface.model.UpdateNewsRequest
import by.happygnom.domain.data_interface.repository.NewsRepository
import by.happygnom.domain.model.News

class NewsRepositoryImpl(
    private val newsGateway: NewsGateway,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun createNews(request: CreateNewsRequest) {
        newsGateway.createNews(request as CreateNewsRequestImpl)
    }

    override suspend fun updateNews(request: UpdateNewsRequest) {
        newsGateway.updateNews(request as UpdateNewsRequestImpl)
    }

    override suspend fun deleteNews(newsId: Long) {
        newsGateway.deleteNews(newsId)
    }

    override suspend fun getAllNews(forceUpdate: Boolean, onlyPublished: Boolean): List<News> {
        return newsDao.ensureIsNotEmpty(forceUpdate, onlyPublished).getAll().map { it.toDomain() }
    }

    override suspend fun getNewsById(newsId: Long, forceUpdate: Boolean): News? {
        return newsDao.ensureIsNotEmpty(forceUpdate).getById(newsId)?.toDomain()
    }

    private suspend fun NewsDao.ensureIsNotEmpty(forceUpdate: Boolean = false, onlyPublished: Boolean = true) = apply {
        if (this.count() == 0L || forceUpdate) {
            updateAllNews(onlyPublished)
        }
    }

    override suspend fun updateAllNews(onlyPublished: Boolean) {
        newsDao.clearNews()

        if (onlyPublished)
            newsDao.insertNews(newsGateway.getAllPublishedNews(50))
        else
            newsDao.insertNews(newsGateway.getAllNews(50))

    }
}
