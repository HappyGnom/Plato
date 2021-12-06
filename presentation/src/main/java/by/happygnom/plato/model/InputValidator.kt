package by.happygnom.plato.model

import androidx.annotation.StringRes
import by.happygnom.plato.R
import java.util.*

object InputValidator {

    @StringRes
    fun getPictureBase64ErrorIdOrNull(input: String?): Int? {
        return when {
            input == null || input.isBlank() -> R.string.error_image_not_set
            else -> null
        }
    }

    @StringRes
    fun getHoldsColorErrorIdOrNull(input: String): Int? {
        return when {
            input.isBlank() -> R.string.error_field_blank
            else -> null
        }
    }

    @StringRes
    fun getFullNameErrorIdOrNull(input: String): Int? {
        return when {
            input.isBlank() -> R.string.error_field_blank
            input.length < 3 -> R.string.error_name_too_short
            else -> null
        }
    }

    @StringRes
    fun getDateErrorIdOrNull(input: Date?): Int? {
        return when {
            input == null -> R.string.error_date_not_set
            else -> null
        }
    }

    @StringRes
    fun getTagsErrorIdOrNull(input: String): Int? {
        return when {
            input.split(",").any { it.isBlank() } -> R.string.error_blank_tags
            else -> null
        }
    }
}
