package com.example.trivial.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = Color.White,
    tertiary = Color.DarkGray,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.Black,
    tertiary = Color.DarkGray,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = MaterialTheme.colorScheme.primary,
    onSecondary = MaterialTheme.colorScheme.primary,
    onTertiary = MaterialTheme.colorScheme.primary,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun TrivialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val typography = if (darkTheme) {
        darkTypography
    } else {
        lightTypography
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )

    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = colorScheme.primary.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
}
