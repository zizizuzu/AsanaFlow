package app.asanaflow.presentation.screens.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.asanaflow.presentation.components.FullScreenLoader
import app.asanaflow.presentation.screens.schedule.components.ScheduleContentComponent
import app.asanaflow.presentation.screens.schedule.model.ScheduleEffect
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    viewModel: ScheduleViewModel = koinInject(),
    openPreviewTrainingAction: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var initialPage by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect{ effect->
            when(effect){
                is ScheduleEffect.NavigateToTraining -> openPreviewTrainingAction.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(ScheduleEvent.LoadInitialDays)
    }

    LaunchedEffect(
        state.days.isNotEmpty() &&
                state.selectedId.isNotBlank() &&
                initialPage == -1
    ) {
        initialPage = state.days
            .indexOfFirst { it.id == state.selectedId }
            .coerceAtLeast(-1)
    }

    when {
        initialPage == -1 || state.isLoading -> FullScreenLoader()
        else -> {
            ScheduleContentComponent(
                selectedId = state.selectedId,
                dayList = state.days,
                onEvent = viewModel::sendEvent
            )
        }
    }
}




