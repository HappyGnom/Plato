package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "route_like",
    foreignKeys = [ForeignKey(
        entity = RouteEntity::class,
        parentColumns = arrayOf("routeId"),
        childColumns = arrayOf("routeId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class RouteLikeEntity(
    @PrimaryKey
    val routeId: Long
)

