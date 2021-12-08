package by.happygnom.data.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import by.happygnom.data.model.RouteEntity
import by.happygnom.data.model.RouteWithTags
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef

@Dao
interface RoutesDao {

    @Query("SELECT * FROM route ORDER BY route.setDateTimestamp DESC")
    suspend fun getAll(): List<RouteWithTags>

    @RawQuery
    suspend fun getRoutesRaw(query: SimpleSQLiteQuery): List<RouteWithTags>

    @Query("SELECT count(*) from route")
    suspend fun count(): Long

    @Query("SELECT * FROM route WHERE route.routeId==:routeId LIMIT 1")
    suspend fun getById(routeId: Long): RouteWithTags?

    @Query("SELECT DISTINCT route.setterName FROM route ORDER BY route.setterName ASC")
    suspend fun getRoutesetterNames(): List<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutes(routes: List<RouteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRouteTagCrossRefs(routes: List<RouteTagsCrossRef>)
}
