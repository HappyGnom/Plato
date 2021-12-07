package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.UserDao
import by.happygnom.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract val userDao: UserDao
}