package by.happygnom.data.model

import android.text.format.DateUtils
import by.happygnom.domain.model.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RouteEntity(
    @SerialName("id")
    val id: Long,
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
//    @SerialName("tags")
//    val tags: List<Tag>?,
    @SerialName("likesCount")
    val likesCount: Int,
    @SerialName("sendsCount")
    val sendsCount: Int,
    @SerialName("commentsCount")
    val commentsCount: Int,
) {
    @Serializable
    data class Tag(
        @SerialName("id")
        val id: Long,
        @SerialName("value")
        val value: String
    )

    fun toDomain(): Route {
        return Route(
            id,
            gradeLevel,
            holdsColor,
            pictureUrl,
            likesCount,
            sendsCount,
            commentsCount,
            setterName ?: "",
            Date(setDateTimestamp * DateUtils.SECOND_IN_MILLIS),
            /*tags?.map { it.value } ?:*/ emptyList(),
            if (status == 0) Route.Status.SET else Route.Status.TAKEN_DOWN,
            visualisationUrl
        )
    }
}

//    val likes: List<UserRouteConnection>?,
//    @SerialName("likesCount")
//@Serializable
//data class Comment(
//    @SerialName("id")
//    val id: Long,
//    @SerialName("userId")
//    val userId: String,
//    @SerialName("message")
//    val message: String,
////        @SerialName("dateTime")
////        val dateTimeTimestamp: Long
//)
//    @SerialName("comments")
//    val comments: List<Comment>?,
//    @SerialName("sends")
//    val sends: List<UserRouteConnection>?,
//@Serializable
//    data class UserRouteConnection(
//        @SerialName("id")
//        val id: Long,
//        @SerialName("userId")
//        val userId: String
//    )
