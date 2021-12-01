package by.happygnom.plato.model

import androidx.compose.ui.graphics.Color
import by.happygnom.plato.ui.theme.*

object GradeLevels {

    enum class GradeScale { V_SCALE, FONT_SCALE }

    fun gradeLevelToScaleString(level: Int, scale: GradeScale) = when (scale) {
        GradeScale.V_SCALE -> gradeLevelToVScale(level)
        GradeScale.FONT_SCALE -> gradeLevelToFontScale(level)
    }

    private fun gradeLevelToVScale(level: Int): String {
        return when {
            level <= 0 -> "V0"
            level == 1 -> "V1"
            level == 2 -> "V2"
            level in 3..4 -> "V3"
            level in 5..6 -> "V4"
            level in 7..8 -> "V5"
            level == 9 -> "V6"
            level == 10 -> "V7"
            level in 11..12 -> "V8"
            level == 13 -> "V9"
            level == 14 -> "V10"
            level == 15 -> "V11"
            level == 16 -> "V12"
            level == 17 -> "V13"
            level == 18 -> "V14"
            level == 19 -> "V15"
            level == 20 -> "V16"
            level >= 21 -> "V17"
            else -> "NaN"
        }
    }

    private fun gradeLevelToFontScale(level: Int): String {
        return when {
            level <= 0 -> "4"
            level == 1 -> "5"
            level == 2 -> "5+"
            level == 3 -> "6a"
            level == 4 -> "6a+"
            level == 5 -> "6b"
            level == 6 -> "6b+"
            level == 7 -> "6c"
            level == 8 -> "6c+"
            level == 9 -> "7a"
            level == 10 -> "7a+"
            level == 11 -> "7b"
            level == 12 -> "7b+"
            level == 13 -> "7c"
            level == 14 -> "7c+"
            level == 15 -> "8a"
            level == 16 -> "8a+"
            level == 17 -> "8b"
            level == 18 -> "8b+"
            level == 19 -> "8c"
            level == 20 -> "8c+"
            level >= 21 -> "9a"
            else -> "NaN"
        }
    }

    fun gradeLevelToColor(level: Int): Color {
        return when {
            level <= 0 -> GradeWhite
            level in 1..2 -> GradeYellow
            level in 3..4 -> GradeOrange
            level in 5..6 -> GradeGreen
            level in 7..8 -> GradeBlue
            level in 9..11 -> GradeRed
            level >= 12 -> GradePurple
            else -> BlackNero
        }
    }
}
