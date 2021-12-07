package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    @SerialName("startDate")
    val startDate: Long,
) {

}