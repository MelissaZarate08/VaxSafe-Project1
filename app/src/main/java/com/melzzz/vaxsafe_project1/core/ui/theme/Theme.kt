package com.melzzz.vaxsafe_project1.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary          = LilacPrimary,
    onPrimary        = Color.White,
    primaryContainer = SurfaceElevated,
    secondary        = SkyBlue,
    onSecondary      = Color.White,
    tertiary         = LilacLight,
    background       = BgDeepNavy,
    surface          = SurfaceCard,
    surfaceVariant   = SurfaceElevated,
    onBackground     = TextOnDark,
    onSurface        = TextOnDark,
    onSurfaceVariant = TextMuted,
    outline          = DividerColor,
    error            = ErrorCoral,
    onError          = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary          = LilacPrimary,
    onPrimary        = Color.White,
    primaryContainer = Color(0xFFEDE9FF),
    secondary        = SkyBlueDark,
    onSecondary      = Color.White,
    tertiary         = LilacDark,
    background       = BgLight,
    surface          = SurfaceLight,
    surfaceVariant   = SurfaceLightCard,
    onBackground     = Color(0xFF1A1440),
    onSurface        = Color(0xFF1A1440),
    onSurfaceVariant = Color(0xFF6B6897),
    outline          = Color(0xFFD4CFFF),
    error            = ErrorCoral,
    onError          = Color.White
)

@Composable
fun VaxSafeProject1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}