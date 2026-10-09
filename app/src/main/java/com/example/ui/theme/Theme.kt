package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FoodWorldColorScheme = lightColorScheme(
    primary = RedPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEBEE),
    onPrimaryContainer = RedDark,
    secondary = RedGradientEnd,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFCE4EC),
    onSecondaryContainer = Color(0xFF880E4F),
    tertiary = AmberStar,
    onTertiary = Color.Black,
    background = WarmBg,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF5F5F7),
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFFE0E0E0),
    outlineVariant = Color(0xFFEEEEEE),
    error = NonVegRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FoodWorldColorScheme,
        typography = Typography,
        content = content
    )
}
