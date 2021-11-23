package by.happygnom.plato.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import by.happygnom.plato.R

private val FinlandicaRegular = Font(R.font.finlandica_regular, FontWeight.Normal)
private val FinlandicaBold = Font(R.font.finlandica_bold, FontWeight.Bold)

private val FinlandicaFontFamily = FontFamily(listOf(FinlandicaRegular, FinlandicaBold))

private val MontserratRegular = Font(R.font.montserrat_regular, FontWeight.Normal)
private val MontserratBold = Font(R.font.montserrat_bold, FontWeight.Bold)

private val MontserratFontFamily = FontFamily(listOf(MontserratRegular, MontserratBold))

val PlatoTypography = Typography(
    h1 = TextStyle(
        fontFamily = FinlandicaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = BlackNero,
        fontFeatureSettings = "c2sc, smcp"
    ),
    h2 = TextStyle(
        fontFamily = FinlandicaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = BlackNero,
        fontFeatureSettings = "c2sc, smcp"
    ),
    body1 = TextStyle(
        fontFamily = FinlandicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = BlackNero
    ),
    body2 = TextStyle(
        fontFamily = FinlandicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = BlackNero
    ),
    caption = TextStyle(
        fontFamily = FinlandicaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = BlackNero
    ),
    button = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = White
    )
)
