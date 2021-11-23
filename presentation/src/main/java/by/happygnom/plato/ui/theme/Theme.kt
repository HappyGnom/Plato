package by.happygnom.plato.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable

@Composable
fun PlatoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightPlatoColorScheme,
        typography = PlatoTypography,
        shapes = Shapes,
        content = content
    )
}
