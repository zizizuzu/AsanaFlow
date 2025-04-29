package app.asanaflow.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    tertiary = DarkTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onTertiary = DarkOnTertiary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface
)

val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onTertiary = OnTertiary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

@Composable
expect fun getColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean
): ColorScheme

@Composable
expect fun ApplySystemUi(
    colorScheme: ColorScheme,
    darkTheme: Boolean
)

@Composable
fun AsanaFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme(darkTheme, dynamicColor)

    ApplySystemUi(colorScheme, darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}