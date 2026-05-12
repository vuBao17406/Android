package com.example.firebaseproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GradientStart,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,
    secondary = AccentLight,
    onSecondary = Color.White,
    secondaryContainer = AccentColor,
    onSecondaryContainer = TextPrimary,
    tertiary = SuccessColor,
    onTertiary = Color.White,
    background = PrimaryDark,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextSecondary,
    error = ErrorColor,
    onError = Color.White,
    outline = TextHint
)

private val LightColorScheme = lightColorScheme(
    primary = GradientStart,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8EAFF),
    onPrimaryContainer = PrimaryLight,
    secondary = AccentLight,
    onSecondary = Color.White,
    background = Color(0xFFF5F6FA),
    onBackground = PrimaryDark,
    surface = Color.White,
    onSurface = PrimaryDark,
    surfaceVariant = Color(0xFFEEEFF5),
    onSurfaceVariant = Color(0xFF5A5A72),
    error = ErrorColor,
    onError = Color.White,
    outline = Color(0xFFD0D0E0)
)

@Composable
fun FirebaseprojectTheme(
    darkTheme: Boolean = true, // Always use dark theme for premium feel
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PrimaryDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}