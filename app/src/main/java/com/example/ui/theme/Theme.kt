package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RosePrimeLight,
    onPrimary = VelvetDark,
    primaryContainer = RosePrimeDark,
    onPrimaryContainer = RomanticWhite,
    secondary = GoldAccent,
    onSecondary = VelvetDark,
    secondaryContainer = VelvetSurfaceVariant,
    onSecondaryContainer = GoldAccentLight,
    tertiary = AmethystPrime,
    onTertiary = VelvetDark,
    background = VelvetDark,
    onBackground = RomanticWhite,
    surface = VelvetSurface,
    onSurface = RomanticWhite,
    surfaceVariant = VelvetSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = CardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = RosePrime,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDDE6),
    onPrimaryContainer = Color(0xFF5A0022),
    secondary = Color(0xFFB28900),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF3C4),
    onSecondaryContainer = Color(0xFF4A3800),
    tertiary = AmethystDeep,
    onTertiary = Color.White,
    background = Color(0xFFFFF5F7),
    onBackground = Color(0xFF2C1020),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF2C1020),
    surfaceVariant = Color(0xFFFBE6ED),
    onSurfaceVariant = Color(0xFF583B47),
    outline = Color(0xFFE8B6C6)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to romantic luxury dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
