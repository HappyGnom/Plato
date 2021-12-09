package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.NewsDao
import by.happygnom.data.dao.RouteInteractionsDao
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.dao.TagsDao
import by.happygnom.data.model.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}
