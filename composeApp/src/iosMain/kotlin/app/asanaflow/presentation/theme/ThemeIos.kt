package app.asanaflow.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean
): ColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

@Composable
actual fun ApplySystemUi(
    colorScheme: ColorScheme,
    darkTheme: Boolean
) {
    // no needed for IOS
}