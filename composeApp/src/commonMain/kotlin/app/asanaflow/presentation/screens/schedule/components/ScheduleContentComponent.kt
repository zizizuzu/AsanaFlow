package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import app.asanaflow.presentation.components.screenWidthDp
import app.asanaflow.presentation.screens.schedule.model.DayType
import app.asanaflow.presentation.screens.schedule.model.DayUiItem
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import app.asanaflow.presentation.theme.Dimens
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun ScheduleContentComponent(
    selectedId: String,
    dayList: List<DayUiItem>,
    onEvent: (ScheduleEvent) -> Unit
) {

    val density = LocalDensity.current
    val todayIndex = dayList.indexOfFirst { it.type == DayType.TODAY }.coerceAtLeast(0)
    val screenWidthDp = screenWidthDp()
    val dayTabOffset = remember { countTabOffset(screenWidthDp, density) }
    val pagerState = rememberPagerState { dayList.size }
    val lazyListState = rememberLazyListState(todayIndex, dayTabOffset)

    SyncEffects(
        selectedId = selectedId,
        dayList = dayList,
        pagerState = pagerState,
        lazyListState = lazyListState,
        dayTabOffset = dayTabOffset,
        onEvent = onEvent
    )

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            DayTabsComponent(
                selectedId = selectedId,
                dayList = dayList,
                lazyListState = lazyListState,
                onEvent = onEvent
            )
            DaysPagerComponent(
                todayIndex = todayIndex,
                pagerState = pagerState,
                onEvent = onEvent,
            )
        }
        BackToTodayButtonComponent(
            selectedId = selectedId,
            todayIndex = todayIndex,
            dayList = dayList,
            onEvent = onEvent
        )
    }
}

@Composable
private fun SyncEffects(
    selectedId: String,
    dayList: List<DayUiItem>,
    pagerState: PagerState,
    lazyListState: LazyListState,
    dayTabOffset: Int,
    onEvent: (ScheduleEvent) -> Unit
) {
    val selectedIndexState by remember(selectedId, dayList) {
        derivedStateOf { dayList.indexOfFirst { it.id == selectedId } }
    }

    LaunchedEffect(selectedIndexState) {
        selectedIndexState
            .takeUnless { it == -1 }
            ?.let { selectedIndex ->
                pagerState.stopScroll()
                pagerState.scrollToPage(selectedIndex)
                lazyListState.animateScrollToItem(selectedIndex, dayTabOffset)
            }
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            dayList
                .getOrNull(pagerState.currentPage)
                ?.id
                ?.let { onEvent.invoke(ScheduleEvent.SelectDay(it)) }
        }
    }
    PaginationListHandler(
        listState = lazyListState,
        selectedIndexState = selectedIndexState,
        pagesCount = dayList.size
    ) {
        onEvent.invoke(ScheduleEvent.LoadMoreDays)
    }
}

@Composable
fun PaginationListHandler(
    listState: LazyListState,
    selectedIndexState: Int,
    pagesCount: Int,
    buffer: Int = 5,
    loadMoreListener: () -> Unit
) {
    val isNeedLoadMore by remember(selectedIndexState) {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            totalItemsCount > 0 && (selectedIndexState >= pagesCount - buffer)
        }
    }
    LaunchedEffect(isNeedLoadMore) {
        snapshotFlow {
            isNeedLoadMore
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                loadMoreListener()
            }
    }
}

private fun countTabOffset(boxWidth: Dp, density: Density): Int = with(density) {
    -((boxWidth - Dimens.DayTabWidth) / 2 - Dimens.DayTabsSpacing).roundToPx()
}