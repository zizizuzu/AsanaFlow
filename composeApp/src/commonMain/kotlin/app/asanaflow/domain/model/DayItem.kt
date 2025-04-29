package app.asanaflow.domain.model

data class DayItem(
    val id: String,
    val isToday: Boolean,
    val isPast: Boolean,
    val isFuture: Boolean
)