package app.asanaflow.domain.repository

import app.asanaflow.domain.model.DayItem

interface DaysRepository {
    suspend fun getInitialDays(): List<DayItem>
    suspend fun loadMoreDays(lastLoadedId: String): List<DayItem>
}