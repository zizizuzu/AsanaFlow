package app.asanaflow.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetSystemBarColor(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    darkIcons: Boolean = true
)