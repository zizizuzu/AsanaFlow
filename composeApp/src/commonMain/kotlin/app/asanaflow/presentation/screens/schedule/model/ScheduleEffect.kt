package app.asanaflow.presentation.screens.schedule.model

sealed interface ScheduleEffect {
    data object NavigateToTraining : ScheduleEffect

//    data class ShowError(val message: String) : YogaEffect()
}