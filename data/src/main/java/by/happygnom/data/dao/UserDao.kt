package by.happygnom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.happygnom.data.model.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT count(*) from user")
    suspend fun count(): Long

    @Query("SELECT * FROM user WHERE user.idToken==:idToken LIMIT 1")
    suspend fun getByIdToken(idToken: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routes: List<UserEntity>)
}