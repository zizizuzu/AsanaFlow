package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import app.asanaflow.presentation.screens.schedule.model.DayType

@Stable
object DayTabStyles {
    @Composable
    fun bgColor(type: DayType, isSelected: Boolean): Color {
        return when {
            isSelected -> MaterialTheme.colorScheme.primary
            type == DayType.TODAY -> MaterialTheme.colorScheme.secondaryContainer
            type == DayType.FUTURE -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.surface
        }
    }

    @Composable
    fun textColor(type: DayType, isSelected: Boolean): Color {
        return when {
            isSelected -> MaterialTheme.colorScheme.onPrimary
            type == DayType.TODAY -> MaterialTheme.colorScheme.onSecondaryContainer
            type == DayType.FUTURE -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.onSurface
        }
    }
}