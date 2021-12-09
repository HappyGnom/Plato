package by.happygnom.domain.model

import java.util.*

data class News(
    val id: Long,
    val header: String,
    val text: String,
    val publishTime: Date,
    val pictureUrl: String
) {
    val isPublished: Boolean
        get() {
            val currentDateTime = Calendar.getInstance()
            return publishTime.before(currentDateTime.time)
        }
}
