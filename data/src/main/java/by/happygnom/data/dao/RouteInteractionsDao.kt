package by.happygnom.data.dao

import androidx.room.*
import by.happygnom.data.model.RouteBookmarkEntity
import by.happygnom.data.model.RouteLikeEntity
import by.happygnom.data.model.RouteSentEntity

@Dao
interface RouteInteractionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setLike(routeLike: RouteLikeEntity)

    @Delete
    fun removeLike(routeLike: RouteLikeEntity)

    @Query("SELECT EXISTS(SELECT * FROM route_like WHERE route_like.routeId = :routeId)")
    fun isLiked(routeId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setBookmark(routeBookmark: RouteBookmarkEntity)

    @Delete
    fun removeBookmark(routeBookmark: RouteBookmarkEntity)

    @Query("SELECT EXISTS(SELECT * FROM route_bookmark WHERE route_bookmark.routeId = :routeId)")
    fun isBookmarked(routeId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setSent(routeSent: RouteSentEntity)

    @Delete
    fun removeSent(routeSent: RouteSentEntity)

    @Query("SELECT EXISTS(SELECT * FROM route_sent WHERE route_sent.routeId = :routeId)")
    fun isSent(routeId: Long): Boolean
}
