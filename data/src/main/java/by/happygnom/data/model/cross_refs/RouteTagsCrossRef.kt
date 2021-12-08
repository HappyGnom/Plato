package by.happygnom.data.model.cross_refs

import androidx.room.Entity

@Entity(primaryKeys = ["routeId", "tagId"])
data class RouteTagsCrossRef(
    val routeId: Long,
    val tagId: Long
)
