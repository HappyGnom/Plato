package by.happygnom.data.model

import android.text.format.DateUtils
import by.happygnom.domain.model.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CommentEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("message")
    val message: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userPhotoUrl")
    val userPictureUrl: String?,
    @SerialName("timestamp")
    val dateTimeTimestamp: Long
) {
    fun toDomain() = Comment(
        id,
        message,
        userName,
        userPictureUrl,
        Date(dateTimeTimestamp * DateUtils.SECOND_IN_MILLIS)
    )
}
