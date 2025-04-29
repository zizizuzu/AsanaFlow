package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import app.asanaflow.presentation.screens.schedule.model.DayUiItem

@Composable
fun BoxScope.BackToTodayButtonComponent(
    selectedId: String,
    todayIndex: Int,
    dayList: List<DayUiItem>,
    onEvent: (ScheduleEvent) -> Unit
) {
    var isFabVisible by remember(selectedId) {
        mutableStateOf(dayList.indexOfFirst { it.id == selectedId } != todayIndex)
    }

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isFabVisible) 1f else 0f,
        animationSpec = tween(
            easing = FastOutSlowInEasing
        ),
        label = "fab_scale_animation"
    )
    if (fabAnimationProgress > 0.01f) {
        FloatingActionButton(
            onClick = {
                isFabVisible = !isFabVisible
                onEvent.invoke(ScheduleEvent.SelectDay(dayList[todayIndex].id))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = fabAnimationProgress
                    scaleY = fabAnimationProgress
                    alpha = fabAnimationProgress
                }
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Back to today")
        }
    }
}