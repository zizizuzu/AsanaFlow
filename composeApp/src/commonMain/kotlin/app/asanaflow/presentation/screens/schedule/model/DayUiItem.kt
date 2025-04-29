package app.asanaflow.presentation.screens.schedule.model

data class DayUiItem(
    val id: String,
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val type: DayType,
    val content: String
)