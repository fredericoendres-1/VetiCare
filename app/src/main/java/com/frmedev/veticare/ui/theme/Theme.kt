package com.frmedev.veticare.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = VetiOlive,
    onPrimary = Color.White,
    secondary = VetiDarkBrown,
    onSecondary = Color.White,
    tertiary = VetiPeach,
    background = VetiBackground,
    surface = VetiCardSurface,
    onBackground = VetiTextPrimary,
    onSurface = VetiTextPrimary,
    outline = VetiOutline
)

@Composable
fun VetiCareTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // Typography será adicionado depois que definirmos as fontes exatas (Serif/Sans)
        content = content
    )
}