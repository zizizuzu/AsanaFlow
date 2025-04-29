package app.asanaflow.presentation.screens.schedule.model

sealed interface ScheduleEvent {
    data object LoadInitialDays : ScheduleEvent
    data object LoadMoreDays : ScheduleEvent
    data class SelectDay(val id: String) : ScheduleEvent

    sealed interface OpenTraining : ScheduleEvent {
        data object Easy : OpenTraining
        data object Medium : OpenTraining
        data object Hard : OpenTraining
    }
}