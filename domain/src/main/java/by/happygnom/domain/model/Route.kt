package by.happygnom.domain.model

import java.util.*

data class Route(
    val id: Long,
    val gradeLevel: Int,
    val holdsColor: String,
    val pictureUrl: String?,
    val likesCount: Int,
    val sendsCount: Int,
    val commentsCount: Int,
    val setterName: String,
    val setDate: Date,
    val tags: List<Tag>,
    val status: Status,
    val visualisationUrl: String?,
) {
    enum class Status { SET, TAKEN_DOWN }

    companion object {
        const val NEW_STATE_DAYS = 7
    }

    val isNew: Boolean
        get() {
            val comparisonDate = Calendar.getInstance()
            comparisonDate.add(Calendar.DATE, -NEW_STATE_DAYS)

            return setDate.after(comparisonDate.time)
        }
}
