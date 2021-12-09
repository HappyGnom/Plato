package by.happygnom.data.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.happygnom.domain.model.User
import java.util.*

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val surname: String,
    val nickname: String?,
    val pictureUrl: String?,
    val sex: Int,
    val startDateTimestamp: Long?,
    val photo: String?
) {
    fun toDomain() = User(
        id,
        name,
        surname,
        nickname,
        sex,
        startDateTimestamp?.let { Date(it * DateUtils.SECOND_IN_MILLIS) },
        pictureUrl,
        photo
    )
}
