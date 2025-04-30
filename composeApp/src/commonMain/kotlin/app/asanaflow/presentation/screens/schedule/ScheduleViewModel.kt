package app.asanaflow.presentation.screens.schedule

import androidx.lifecycle.viewModelScope
import app.asanaflow.domain.model.DayItem
import app.asanaflow.domain.usecase.LoadDayScheduleUseCase
import app.asanaflow.presentation.base.BaseViewModel
import app.asanaflow.presentation.screens.schedule.mapper.DayMapper
import app.asanaflow.presentation.screens.schedule.model.DayUiItem
import app.asanaflow.presentation.screens.schedule.model.ScheduleEffect
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import app.asanaflow.presentation.screens.schedule.model.ScheduleState
import kotlinx.coroutines.launch
import app.asanaflow.domain.Result

class ScheduleViewModel(
    private val loadDaysUseCase: LoadDayScheduleUseCase,
    private val mapper: DayMapper
) : BaseViewModel<ScheduleEvent, ScheduleState, ScheduleEffect>() {
    private var selectedId: String = ""

    override fun setInitialState(): ScheduleState = ScheduleState(isLoading = true)

    override fun handleEvent(event: ScheduleEvent) {

        when (event) {
            is ScheduleEvent.LoadInitialDays -> loadInitialDays()
            is ScheduleEvent.LoadMoreDays -> loadMoreDays()
            is ScheduleEvent.SelectDay -> selectDay(event.id)
            is ScheduleEvent.OpenTraining -> when (event) {
                ScheduleEvent.OpenTraining.Easy,
                ScheduleEvent.OpenTraining.Medium,
                ScheduleEvent.OpenTraining.Hard -> {
                    //TODO will be realised in future
//                    sendEffect(ScheduleEffect.NavigateToTraining)
                }
            }
        }
    }

    private fun loadInitialDays() {
        viewModelScope.launch {
            updateDayListUi(loadDaysUseCase.loadInitialItems())
        }
    }

    private fun loadMoreDays() {
        val lastLoadedId = state.value.days.last().id
        viewModelScope.launch {
            updateDayListUi(loadDaysUseCase.loadMoreItems(lastLoadedId))
        }
    }

    private fun updateDayListUi(result: Result<List<DayItem>>) {
        when (result) {
            is Result.Success -> {
                val uiItemList: MutableList<DayUiItem> = mutableListOf()
                result.data.forEach { item ->
                    if (selectedId.isBlank() && item.isToday) {
                        selectedId = item.id
                    }
                    uiItemList.add(mapper.toUiItem(item))
                }

                updateState {
                    ScheduleState(
                        selectedId = this@ScheduleViewModel.selectedId,
                        days = days + uiItemList.toList()
                    )
                }
            }

            is Result.Error -> updateState {
                copy(
                    isLoading = false,
                    error = result.exception.message
                )
            }

            is Result.Loading -> Unit
        }
    }

    private fun selectDay(selectedId: String) {
        this.selectedId = selectedId
        updateState {
            copy(selectedId = selectedId)
        }
    }
}