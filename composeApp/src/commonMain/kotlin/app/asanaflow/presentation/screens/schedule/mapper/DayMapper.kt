package app.asanaflow.presentation.screens.schedule.mapper

import app.asanaflow.domain.model.DayItem
import app.asanaflow.presentation.screens.schedule.model.DayType
import app.asanaflow.presentation.screens.schedule.model.DayUiItem
import kotlinx.datetime.LocalDate

class DayMapper {

    fun toUiItem(domainModel: DayItem): DayUiItem {
        val date = parseDate(domainModel.id)

        val type = when {
            domainModel.isPast -> DayType.PAST
            domainModel.isToday -> DayType.TODAY
            domainModel.isFuture -> DayType.FUTURE
            else -> throw IllegalArgumentException("Unsupported date type for id=${domainModel.id}")
        }

        val content = when (type) {
            DayType.PAST -> "Past"
            DayType.TODAY -> "Current"
            DayType.FUTURE -> "Future"
        }

        return DayUiItem(
            id = domainModel.id,
            dayOfMonth = date.dayOfMonth,
            dayOfWeek = date.getShortDayOfWeek(),
            type = type,
            content = content
        )
    }

    private fun parseDate(id: String): LocalDate =
        id.split("-").let { parts ->
            require(parts.size == 3) { "Invalid date format: $id" }
            val year = parts[0].toIntOrNull() ?: error("Bad year: ${parts[0]}")
            val month = parts[1].toIntOrNull() ?: error("Bad month: ${parts[1]}")
            val day = parts[2].toIntOrNull() ?: error("Bad day: ${parts[2]}")
            LocalDate(year, month, day)
        }

    private fun LocalDate.getShortDayOfWeek(): String =
        this.dayOfWeek
            .name
            .take(3)
            .lowercase()
            .replaceFirstChar { it.uppercaseChar() }
}
