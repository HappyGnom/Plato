package by.happygnom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.happygnom.data.model.RouteEntity
import by.happygnom.data.model.UserEntity
import by.happygnom.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE user.id==:id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?

    @Query("SELECT count(*) from user")
    suspend fun count(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)
}
