package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "route_sent",
    foreignKeys = [ForeignKey(
        entity = RouteEntity::class,
        parentColumns = arrayOf("routeId"),
        childColumns = arrayOf("routeId"),
        onDelete = CASCADE
    )]
)
data class RouteSentEntity(
    @PrimaryKey
    val routeId: Long
)

