package by.happygnom.plato.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.text.isDigitsOnly
import by.happygnom.plato.R
import java.time.LocalDate
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
            input.matches(regex = Regex("^\\d+")) -> R.string.error_numbers_are_not_allowed
            input.length < 2 -> R.string.error_name_too_short
            else -> null
        }
    }

    @StringRes
    fun getNicknameErrorIdOrNull(input: String): Int? {
        return when {
            input.isBlank() -> R.string.error_field_blank
            input.isDigitsOnly() -> R.string.error_must_contain_letter
            input.length < 3 -> R.string.error_name_too_short
            else -> null
        }
    }

    @StringRes
    fun getDateErrorIdOrNull(input: Date?): Int? {
        return when {
            input == null -> R.string.error_date_not_set
            input.after(Calendar.getInstance().time) -> (R.string.error_after_current_date)
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
