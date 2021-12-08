package by.happygnom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.happygnom.domain.model.Tag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey
    @SerialName("id")
    val tagId: Long,
    @SerialName("value")
    val value: String
) {
    fun toDomain() = Tag(
        tagId, value
    )
}
