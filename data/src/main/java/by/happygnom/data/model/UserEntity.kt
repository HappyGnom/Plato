package by.happygnom.data.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "user")
data class UserEntity(
    @SerialName("firebaseId")
    @PrimaryKey
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
    val startDate: Long,
//    @SerialName("likedRouteIds")
//    @Ignore
//    val liked: List<Long>? = null,
//    @SerialName("sentRouteIds")
//    @Ignore
//    val sent: List<Long>? = null,
//    @SerialName("favoriteRouteIds")
//    @Ignore
//    val favorite: List<Long>? = null
) {

    fun toDomain() = User(
        id,
        name,
        surname,
        nickname,
        sex,
        Date(startDate * DateUtils.SECOND_IN_MILLIS),
        pictureUrl,
        null, null, null
//        liked,
//        sent,
//        favorite
    )

}