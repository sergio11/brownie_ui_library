package com.dreamsoftware.brownie.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun BrownieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    lightColorScheme: ColorScheme = BrownieLightColorScheme,
    darkColorScheme: ColorScheme = BrownieDarkColorScheme,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val context = LocalContext.current
    if(context is ComponentActivity) {
        val statusBarLight = lightColorScheme.primary.toArgb()
        val statusBarDark = darkColorScheme.primary.toArgb()
        val isDarkMode = isSystemInDarkTheme()
        DisposableEffect(isDarkMode) {
            context.enableEdgeToEdge(
                statusBarStyle = if (!isDarkMode) {
                    SystemBarStyle.dark(
                        statusBarLight
                    )
                } else {
                    SystemBarStyle.dark(
                        statusBarDark
                    )
                },
                navigationBarStyle = if(!isDarkMode){
                    SystemBarStyle.light(
                        lightColorScheme.primary.toArgb(),
                        lightColorScheme.secondary.toArgb()
                    )
                } else {
                    SystemBarStyle.dark(darkColorScheme.primary.toArgb())
                }
            )

            onDispose { }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}