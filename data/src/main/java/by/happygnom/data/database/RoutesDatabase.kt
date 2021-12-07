package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.model.RouteEntity

@Database(entities = [RouteEntity::class], version = 1)
abstract class RoutesDatabase : RoomDatabase(){
    abstract val routesDao: RoutesDao
}
