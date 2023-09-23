package com.sosyal.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

private val lightColorPalette = lightColors(
    primary = light_primary,
    onPrimary = light_onPrimary,
    primaryVariant = light_primaryVariant,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    secondaryVariant = light_secondaryVariant,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
    error = light_error,
    onError = light_onError
)

private val darkColorPalette = darkColors(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    primaryVariant = dark_primaryVariant,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    secondaryVariant = dark_secondaryVariant,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    error = dark_error,
    onError = dark_onError
)

@Immutable
object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0f,
        focusedAlpha = 0f,
        draggedAlpha = 0f,
        hoveredAlpha = 0f
    )
}

@Composable
fun SosyalTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}