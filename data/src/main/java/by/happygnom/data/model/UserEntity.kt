package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user")
data class UserEntity(
    @SerialName("idToken")
    @PrimaryKey
    val idToken: Long,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("pictureUrl")
    val pictureUrl: String?,
    @SerialName("sex")
    val sex: String,
    @SerialName("email")
    val email: String,
) {

}