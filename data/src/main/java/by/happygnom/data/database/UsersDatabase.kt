package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.UserDao
import by.happygnom.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 3)
abstract class UsersDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
