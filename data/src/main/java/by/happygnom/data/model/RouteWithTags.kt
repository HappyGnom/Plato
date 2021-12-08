package by.happygnom.data.model

import android.text.format.DateUtils
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef
import by.happygnom.domain.model.Route
import java.util.*

data class RouteWithTags(
    @Embedded val route: RouteEntity,
    @Relation(
        parentColumn = "routeId",
        entity = TagEntity::class,
        entityColumn = "tagId",
        associateBy = Junction(
            value = RouteTagsCrossRef::class,
            parentColumn = "routeId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagEntity>
) {
    fun toDomain() = Route(
        route.routeId,
        route.gradeLevel,
        route.holdsColor,
        route.pictureUrl,
        route.likesCount,
        route.sendsCount,
        route.commentsCount,
        route.setterName ?: "",
        Date(route.setDateTimestamp * DateUtils.SECOND_IN_MILLIS),
        tags.map { it.toDomain() },
        if (route.status == 0) Route.Status.SET else Route.Status.TAKEN_DOWN,
        route.visualisationUrl
    )
}

