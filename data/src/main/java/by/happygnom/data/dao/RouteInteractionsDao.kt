package by.happygnom.data.dao

import androidx.room.*
import by.happygnom.data.model.RouteBookmarkEntity
import by.happygnom.data.model.RouteLikeEntity
import by.happygnom.data.model.RouteSentEntity

@Dao
interface RouteInteractionsDao {

    // Likes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setLike(routeLike: RouteLikeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setLikes(routeLikes: List<RouteLikeEntity>)

    @Delete
    fun removeLike(routeLike: RouteLikeEntity)

    @Query("DELETE FROM route_like")
    fun nukeLike()

    @Query("SELECT EXISTS(SELECT * FROM route_like WHERE route_like.routeId = :routeId)")
    fun isLiked(routeId: Long): Boolean

    // Bookmarks
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setBookmark(routeBookmark: RouteBookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setBookmarks(routeBookmarks: List<RouteBookmarkEntity>)

    @Delete
    fun removeBookmark(routeBookmark: RouteBookmarkEntity)

    @Query("DELETE FROM route_bookmark")
    fun nukeBookmark()

    @Query("SELECT EXISTS(SELECT * FROM route_bookmark WHERE route_bookmark.routeId = :routeId)")
    fun isBookmarked(routeId: Long): Boolean

    // Sends
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setSent(routeSent: RouteSentEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setSends(routeSends: List<RouteSentEntity>)

    @Delete
    fun removeSent(routeSent: RouteSentEntity)

    @Query("DELETE FROM route_sent")
    fun nukeSent()

    @Query("SELECT EXISTS(SELECT * FROM route_sent WHERE route_sent.routeId = :routeId)")
    fun isSent(routeId: Long): Boolean
}
