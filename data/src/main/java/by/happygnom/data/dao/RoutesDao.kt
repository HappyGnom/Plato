package by.happygnom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.happygnom.data.model.RouteEntity

@Dao
interface RoutesDao {

    @Query("SELECT * FROM route ORDER BY route.setDateTimestamp DESC")
    suspend fun getAll(): List<RouteEntity>

    @Query("SELECT count(*) from route")
    suspend fun count(): Long

    @Query("SELECT * FROM route WHERE route.id==:routeId LIMIT 1")
    suspend fun getById(routeId: Long): RouteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routes: List<RouteEntity>)
}
