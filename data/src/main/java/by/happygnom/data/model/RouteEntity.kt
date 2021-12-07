package by.happygnom.data.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.happygnom.domain.model.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "route")
data class RouteEntity(
    @SerialName("id")
    @PrimaryKey
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
//    val tags: List<TagEntity>?,
    @SerialName("likesCount")
    val likesCount: Int,
    @SerialName("sendsCount")
    val sendsCount: Int,
    @SerialName("commentsCount")
    val commentsCount: Int,
) {
    fun toDomain() = Route(
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
