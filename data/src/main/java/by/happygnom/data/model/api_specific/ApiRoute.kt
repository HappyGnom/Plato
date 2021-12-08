package by.happygnom.data.model.api_specific

import by.happygnom.data.model.RouteEntity
import by.happygnom.data.model.cross_refs.RouteTagsCrossRef
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiRoute(
    @SerialName("id")
    val routeId: Long,
    @SerialName("grade")
    val gradeLevel: Int,
    @SerialName("color")
    val holdsColor: String,
    @SerialName("installDateTimestamp")
    val setDateTimestamp: Long,
    @SerialName("setterName")
    val setterName: String?,
    @SerialName("pictureUrl")
    val pictureUrl: String?,
    @SerialName("modelUrl")
    val visualisationUrl: String?,
    @SerialName("status")
    val status: Int,
    @SerialName("tagIds")
    val tagIds: List<Long>?,
    @SerialName("likesCount")
    val likesCount: Int,
    @SerialName("sendsCount")
    val sendsCount: Int,
    @SerialName("commentsCount")
    val commentsCount: Int,
) {
    fun toEntity(): Pair<RouteEntity, List<RouteTagsCrossRef>> {
        val routeEntity = RouteEntity(
            routeId,
            gradeLevel,
            holdsColor,
            setDateTimestamp,
            setterName,
            pictureUrl,
            visualisationUrl,
            status,
            likesCount,
            sendsCount,
            commentsCount
        )

        val tagRefs = tagIds?.map { tagId -> RouteTagsCrossRef(routeId, tagId) } ?: emptyList()
        return Pair(routeEntity, tagRefs)
    }
}

