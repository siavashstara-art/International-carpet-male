package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkLuxuryColorScheme = darkColorScheme(
    primary = FarshGold,
    onPrimary = FarshObsidian,
    primaryContainer = FarshGoldContainer,
    onPrimaryContainer = FarshGoldWarm,
    secondary = FarshGoldMuted,
    onSecondary = FarshObsidian,
    secondaryContainer = FarshBorder,
    onSecondaryContainer = FarshTextPrimary,
    tertiary = FarshMadderRuby,
    onTertiary = Color.White,
    background = FarshObsidian,
    onBackground = FarshTextPrimary,
    surface = FarshVelvetSurface,
    onSurface = FarshTextPrimary,
    surfaceVariant = FarshCardSurface,
    onSurfaceVariant = FarshTextSecondary,
    outline = FarshBorder,
    outlineVariant = FarshBorderSubtle
)

private val LightLuxuryColorScheme = lightColorScheme(
    primary = FarshGoldDark,
    onPrimary = Color.White,
    primaryContainer = FarshParchmentDark,
    onPrimaryContainer = FarshGoldDark,
    secondary = FarshMadderRuby,
    onSecondary = Color.White,
    background = FarshParchment,
    onBackground = FarshObsidian,
    surface = Color.White,
    onSurface = FarshObsidian,
    surfaceVariant = FarshParchmentDark,
    onSurfaceVariant = FarshWalnut,
    outline = Color(0xFFD4CEBD),
    outlineVariant = Color(0xFFE8E4D8)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Luxury dark by default for grand gallery feel
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkLuxuryColorScheme else LightLuxuryColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
