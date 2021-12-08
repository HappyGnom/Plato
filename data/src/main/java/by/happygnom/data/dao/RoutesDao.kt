package by.happygnom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.happygnom.data.model.RouteEntity
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef
import by.happygnom.data.model.RouteWithTags

@Dao
interface RoutesDao {

    @Query("SELECT * FROM route ORDER BY route.setDateTimestamp DESC")
    suspend fun getAll(): List<RouteWithTags>

    @Query("SELECT count(*) from route")
    suspend fun count(): Long

    @Query("SELECT * FROM route WHERE route.routeId==:routeId LIMIT 1")
    suspend fun getById(routeId: Long): RouteWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutes(routes: List<RouteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRouteTagCrossRefs(routes: List<RouteTagsCrossRef>)
}
