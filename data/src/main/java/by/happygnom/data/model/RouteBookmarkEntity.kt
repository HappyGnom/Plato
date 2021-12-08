package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "route_bookmark",
    foreignKeys = [ForeignKey(
        entity = RouteEntity::class,
        parentColumns = arrayOf("routeId"),
        childColumns = arrayOf("routeId"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class RouteBookmarkEntity(
    @PrimaryKey
    val routeId: Long
)

