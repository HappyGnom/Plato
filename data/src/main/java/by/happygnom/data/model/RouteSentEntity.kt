package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "route_sent",
    foreignKeys = [ForeignKey(
        entity = RouteEntity::class,
        parentColumns = arrayOf("routeId"),
        childColumns = arrayOf("routeId"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class RouteSentEntity(
    @PrimaryKey
    val routeId: Long
)

