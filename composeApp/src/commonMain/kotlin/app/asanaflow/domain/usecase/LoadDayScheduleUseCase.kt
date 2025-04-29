package app.asanaflow.domain.usecase

import app.asanaflow.domain.Result
import app.asanaflow.domain.model.DayItem

interface LoadDayScheduleUseCase {
    suspend fun loadInitialItems(): Result<List<DayItem>>
    suspend fun loadMoreItems(lastLoadedId: String): Result<List<DayItem>>
}