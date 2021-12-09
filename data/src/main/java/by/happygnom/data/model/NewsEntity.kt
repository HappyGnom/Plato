package by.happygnom.data.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.happygnom.domain.model.News
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @SerialName("id")
    val newsId: Long,
    @SerialName("header")
    val header: String,
    @SerialName("text")
    val text: String,
    @SerialName("publishTimestamp")
    val publishDateTimeTimestamp: Long,
    @SerialName("mainPictureUrl")
    val pictureUrl: String
) {
    fun toDomain() = News(
        newsId,
        header,
        text,
        Date(publishDateTimeTimestamp * DateUtils.SECOND_IN_MILLIS),
        pictureUrl
    )
}
