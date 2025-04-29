package app.asanaflow.presentation.screens.schedule.model

data class ScheduleState(
    val selectedId: String = "",
    val days: List<DayUiItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)