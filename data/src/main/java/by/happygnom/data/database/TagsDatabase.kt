package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.dao.TagsDao
import by.happygnom.data.model.TagEntity

@Database(entities = [TagEntity::class], version = 1)
abstract class TagsDatabase : RoomDatabase() {
    abstract val tagsDao: TagsDao
}
