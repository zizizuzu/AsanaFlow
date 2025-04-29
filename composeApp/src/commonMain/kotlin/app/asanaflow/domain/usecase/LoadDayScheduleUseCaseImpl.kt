package app.asanaflow.domain.usecase

import app.asanaflow.domain.Result
import app.asanaflow.domain.model.DayItem
import app.asanaflow.domain.repository.DaysRepository

class LoadDayScheduleUseCaseImpl(
    private val repository: DaysRepository
) : LoadDayScheduleUseCase {
    override suspend fun loadInitialItems(): Result<List<DayItem>> = try {
        Result.Success(repository.getInitialDays())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun loadMoreItems(lastLoadedId: String): Result<List<DayItem>> = try {
        Result.Success(repository.loadMoreDays(lastLoadedId))
    } catch (e: Exception) {
        Result.Error(e)
    }
}