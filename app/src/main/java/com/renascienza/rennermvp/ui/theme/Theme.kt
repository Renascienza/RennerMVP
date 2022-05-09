package com.renascienza.rennermvp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation values that can be themed.
 */
@Immutable
data class Elevations(val card: Dp = 0.dp)

internal val LocalElevations = staticCompositionLocalOf { Elevations() }

private val DarkColorPalette = darkColors(
    background = Color.Black,
    primary = Color.Black,
    primaryVariant = Color.Black,
    secondary = RennerRed,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    background = White,
    primary = White,
    primaryVariant = RennerRed,
    secondary = RennerRed,
    surface = White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSecondary = White
)

private val LightElevation = Elevations()

private val DarkElevation = Elevations(card = 1.dp)

@Composable
fun RennerMVPTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val elevation = if (darkTheme) DarkElevation else LightElevation
    CompositionLocalProvider(
        LocalElevations provides elevation
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

object RennerMVPTheme{

    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val elevations: Elevations
        @Composable
        get() = LocalElevations.current
}