package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class RouteEntity(
    @PrimaryKey
    val routeId: Long,
    val gradeLevel: Int,
    val holdsColor: String,
    val setDateTimestamp: Long,
    val setterName: String?,
    val pictureUrl: String?,
    val visualisationUrl: String?,
    val status: Int,
    val likesCount: Int,
    val sendsCount: Int,
    val commentsCount: Int,
)

