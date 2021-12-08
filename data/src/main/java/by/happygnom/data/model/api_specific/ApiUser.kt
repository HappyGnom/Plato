package by.happygnom.data.model.api_specific

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.happygnom.data.model.UserEntity
import by.happygnom.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ApiUser(
    @SerialName("firebaseId")
    val id: String,
    @SerialName("firstName")
    val name: String,
    @SerialName("lastName")
    val surname: String,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("photoUrl")
    val pictureUrl: String?,
    @SerialName("sex")
    val sex: Int,
    @SerialName("startDateTimestamp")
    val startDateTimestamp: Long,
    @SerialName("photoBase64")
    val photo: String?,
    @SerialName("likedRouteIds")
    val likedIds: List<Long>? = null,
    @SerialName("favoriteRouteIds")
    val bookmarkIds: List<Long>? = null,
    @SerialName("sentRouteIds")
    val sentIds: List<Long>? = null
) {
    fun toEntity() = UserEntity(
        id,
        name,
        surname,
        nickname,
        pictureUrl,
        sex,
        startDateTimestamp,
        photo
    )
}
