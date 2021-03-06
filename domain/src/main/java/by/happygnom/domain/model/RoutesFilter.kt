package by.happygnom.domain.model

import java.util.*

data class RoutesFilter(
    val category: Category?,
    val gradeLevelFrom: Int?,
    val gradeLevelTo: Int?,
    val setterName: String?,
    val setDateFrom: Date?,
    val setDateTo: Date?,
    val tags: List<String>,
    val includeTakenDown: Boolean
) {
    enum class Category {
        SENT, BOOKMARKED, LIKED
    }

    companion object {
        val Default = Builder().build()
    }

    class Builder {
        private var category: Category? = null
        private var gradeLevelFrom: Int? = null
        private var gradeLevelTo: Int? = null
        private var setterName: String? = null
        private var setDateFrom: Date? = null
        private var setDateTo: Date? = null
        private var tags: MutableList<String> = mutableListOf()
        private var includeTakenDown: Boolean = false

        fun setCategory(category: Category?) = apply {
            this.category = category
        }

        fun setGradeLevelFrom(gradeLevel: Int?) = apply {
            this.gradeLevelFrom = gradeLevel
        }

        fun setGradeLevelTo(gradeLevel: Int?) = apply {
            this.gradeLevelTo = gradeLevel
        }

        fun setSetterName(name: String?) = apply {
            this.setterName = name
        }

        fun setDateFrom(date: Date?) = apply {
            this.setDateFrom = date
        }

        fun setDateTo(date: Date?) = apply {
            this.setDateTo = date
        }

        fun addTags(tags: List<String>) = apply {
            this.tags.addAll(tags)
        }

        fun addTags(vararg tags: String) = apply {
            this.tags.addAll(tags)
        }

        fun setIncludeTakenDown(includeTakenDown: Boolean) = apply {
            this.includeTakenDown = includeTakenDown
        }

        fun build() = RoutesFilter(
            category = category,
            gradeLevelFrom = gradeLevelFrom,
            gradeLevelTo = gradeLevelTo,
            setterName = setterName,
            setDateFrom = setDateFrom,
            setDateTo = setDateTo,
            tags = tags,
            includeTakenDown = includeTakenDown
        )
    }
}
