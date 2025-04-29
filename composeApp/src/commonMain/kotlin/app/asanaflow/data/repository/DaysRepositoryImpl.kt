package app.asanaflow.data.repository

import app.asanaflow.domain.model.DayItem
import app.asanaflow.domain.repository.DaysRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class DaysRepositoryImpl : DaysRepository {
    private val today: LocalDate = Clock.System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    private val generatedDays = mutableListOf<DayItem>()

    /**
     * Returns initial days list in reverse chronological order:
     * - Future days first (newest first)
     * - Today's day in the middle
     * - Past days last (oldest last)
     */
    override suspend fun getInitialDays(): List<DayItem> {
        generatedDays.clear()
        generateDaysAroundToday(
            pastDays = INITIAL_PAST_DAYS,
            futureDays = INITIAL_FUTURE_DAYS
        )
        return generatedDays.toList()
    }

    /**
     * Loads additional days for pagination while maintaining reverse order
     * @param lastLoadedId The ID (date string) of the last loaded day
     */
    override suspend fun loadMoreDays(lastLoadedId: String): List<DayItem> {
        val lastDate = try {
            LocalDate.parse(lastLoadedId)
        } catch (e: Exception) {
            return emptyList()
        }

        return if (lastDate > today) {
            // Loading more future days (prepend to beginning)
            generateMoreFutureDays(lastDate, PAGINATION_DAYS_TO_ADD)
        } else {
            // Loading more past days (append to end)
            generateMorePastDays(lastDate, PAGINATION_DAYS_TO_ADD)
        }
    }

    /**
     * Generates initial days around today's date in reverse order
     */
    private fun generateDaysAroundToday(pastDays: Int, futureDays: Int) {
        // Future days: newest first
        val futureItems = (futureDays downTo 1)
            .map { offset ->
                val date = today.plus(DatePeriod(days = offset))
                createDayItem(date, isFuture = true)
            }
        generatedDays.addAll(futureItems)

        // Today
        generatedDays.add(createDayItem(today, isToday = true))

        // Past days: oldest last
        val pastItems = (1..pastDays)
            .map { offset ->
                val date = today.minus(DatePeriod(days = offset))
                createDayItem(date, isPast = true)
            }
        generatedDays.addAll(pastItems)
    }

    /**
     * Generates additional past days (appends to end of list)
     */
    private fun generateMorePastDays(startDate: LocalDate, daysToAdd: Int): List<DayItem> {
        val newDays = (1..daysToAdd)
            .mapNotNull { offset ->
                val date = startDate.minus(DatePeriod(days = offset))
                if (date < today) createDayItem(date, isPast = true) else null
            }
        generatedDays.addAll(newDays)
        return newDays
    }

    /**
     * Generates additional future days (prepends to beginning of list)
     */
    private fun generateMoreFutureDays(startDate: LocalDate, daysToAdd: Int): List<DayItem> {
        val newDates = (1..daysToAdd)
            .map { offset -> startDate.plus(DatePeriod(days = offset)) }
        val newItems = newDates
            .reversed()
            .map { date -> createDayItem(date, isFuture = true) }

        generatedDays.addAll(0, newItems)
        return newItems
    }

    private fun createDayItem(
        date: LocalDate,
        isToday: Boolean = false,
        isPast: Boolean = false,
        isFuture: Boolean = false
    ): DayItem = DayItem(
        id = date.toString(),
        isToday = isToday,
        isPast = isPast,
        isFuture = isFuture
    )

    companion object {
        private const val INITIAL_PAST_DAYS = 10
        private const val INITIAL_FUTURE_DAYS = 10
        private const val PAGINATION_DAYS_TO_ADD = 10
    }
}