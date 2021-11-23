package by.happygnom.plato.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val Grey1 = Color(0xFF5B5E67)
val Grey2 = Color(0xFF8B96A4)
val Grey3 = Color(0xFFD9E1EE)
val Grey4 = Color(0xFFE7ECF3)
val Grey5 = Color(0xFFF8FAFF)
val White = Color(0xFFFFFFFF)

val Teal1 = Color(0xFF6BC7BE)
val Teal2 = Color(0xFFA3DCD6)

val Pink1 = Color(0xFFEE2951)
val Pink2 = Color(0xFFF4708B)

val BlackNero = Color(0xFF1D1D1D)

val LightPlatoColorScheme = lightColors(
    primary = Teal1,
    onPrimary = White,

    secondary = Pink1,
    onSecondary = White,

    background = White,
    onBackground = BlackNero,

    error = Pink1,
    onError = White,
)
