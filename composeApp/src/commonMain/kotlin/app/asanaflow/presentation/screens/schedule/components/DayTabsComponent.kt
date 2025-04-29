package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.asanaflow.presentation.screens.schedule.model.DayUiItem
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import app.asanaflow.presentation.theme.Dimens

@Composable
fun DayTabsComponent(
    selectedId: String,
    dayList: List<DayUiItem>,
    lazyListState: LazyListState,
    onEvent: (ScheduleEvent) -> Unit
) {
    LazyRow(
        state = lazyListState,
        reverseLayout = true,
        userScrollEnabled = false,
        contentPadding = PaddingValues(horizontal = Dimens.DayTabsSpacing),
        horizontalArrangement = Arrangement.spacedBy(Dimens.DayTabsSpacing),
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        items(
            count = dayList.size,
            key = { index -> dayList[index].id }
        ) { index: Int ->
            val day = dayList[index]
            DayTabComponent(
                isSelected = day.id == selectedId,
                dayOfMonth = day.dayOfMonth,
                dayOfWeek = day.dayOfWeek,
                type = day.type,
                onClick = { onEvent.invoke(ScheduleEvent.SelectDay(day.id)) }
            )
        }
    }
}