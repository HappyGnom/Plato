package by.happygnom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.happygnom.data.dao.RouteInteractionsDao
import by.happygnom.data.dao.RoutesDao
import by.happygnom.data.dao.TagsDao
import by.happygnom.data.model.*
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef

@Database(
    entities = [
        RouteEntity::class, RouteTagsCrossRef::class, TagEntity::class,
        RouteLikeEntity::class, RouteBookmarkEntity::class, RouteSentEntity::class
    ],
    version = 2
)
abstract class RoutesDatabase : RoomDatabase() {
    abstract val routeInteractionsDao: RouteInteractionsDao
    abstract val routesDao: RoutesDao
    abstract val tagsDao: TagsDao
}
