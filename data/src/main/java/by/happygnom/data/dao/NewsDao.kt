package by.happygnom.data.dao

import androidx.room.*
import by.happygnom.data.model.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY news.publishDateTimeTimestamp DESC")
    suspend fun getAll(): List<NewsEntity>

    @Query("SELECT count(*) from news")
    suspend fun count(): Long

    @Query("SELECT * FROM news WHERE news.newsId==:newsId LIMIT 1")
    suspend fun getById(newsId: Long): NewsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun clearNews()
}
