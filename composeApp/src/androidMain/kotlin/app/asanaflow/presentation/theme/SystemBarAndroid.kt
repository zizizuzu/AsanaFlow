package app.asanaflow.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SetSystemBarColor(
    statusBarColor: Color?,
    navigationBarColor: Color?,
    darkIcons: Boolean
) {
    val view = LocalView.current
    if (!view.isInEditMode) {

        SideEffect {
            val window = (view.context as Activity).window

            statusBarColor?.let {
                window.statusBarColor = it.toArgb()
            }
            navigationBarColor?.let {
                window.navigationBarColor = it.toArgb()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

            WindowCompat
                .getInsetsController(window, view)
                .apply {
                    isAppearanceLightStatusBars = darkIcons
                    isAppearanceLightNavigationBars = darkIcons
                }
        }
    }
}
