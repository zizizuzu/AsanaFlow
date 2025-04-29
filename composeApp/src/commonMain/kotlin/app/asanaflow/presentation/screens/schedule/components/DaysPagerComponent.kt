package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.asanaflow.presentation.screens.schedule.model.ScheduleEvent
import asanaflow.composeapp.generated.resources.Res
import asanaflow.composeapp.generated.resources.ic_practice_easy
import asanaflow.composeapp.generated.resources.ic_practice_hard
import asanaflow.composeapp.generated.resources.ic_practice_standard
import asanaflow.composeapp.generated.resources.ic_time
import asanaflow.composeapp.generated.resources.schedule_screen_practice_card_hard
import asanaflow.composeapp.generated.resources.schedule_screen_practice_card_quick
import asanaflow.composeapp.generated.resources.schedule_screen_practice_card_standard
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.math.sign

@Composable
fun ColumnScope.DaysPagerComponent(
    todayIndex: Int,
    pagerState: PagerState,
    onEvent: (ScheduleEvent) -> Unit
) {
    val isScrollEnabled by remember {
        derivedStateOf {
            pagerState.currentPage + pagerState.currentPageOffsetFraction.sign.toInt() >= todayIndex
        }
    }
    HorizontalPager(
        state = pagerState,
        reverseLayout = true,
        beyondViewportPageCount = 1,
        userScrollEnabled = isScrollEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) { index: Int ->
        if (todayIndex <= index) {
            when {
                index == todayIndex -> {
                    val cardModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .weight(1F)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {

                        ScheduleCard(
                            title = Res.string.schedule_screen_practice_card_quick,
                            time = "15 min",
                            image = Res.drawable.ic_practice_easy,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = cardModifier
                        ) {
                            onEvent.invoke(ScheduleEvent.OpenTraining.Easy)
                        }

                        ScheduleCard(
                            title = Res.string.schedule_screen_practice_card_standard,
                            time = "30 min",
                            image = Res.drawable.ic_practice_standard,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = cardModifier
                        ) {
                            onEvent.invoke(ScheduleEvent.OpenTraining.Medium)
                        }

                        ScheduleCard(
                            title = Res.string.schedule_screen_practice_card_hard,
                            time = "45 min",
                            image = Res.drawable.ic_practice_hard,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = cardModifier
                        ) {
                            onEvent.invoke(ScheduleEvent.OpenTraining.Hard)
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text("Empty")
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(
    image: DrawableResource,
    title: StringResource,
    time: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val cardCornerShape = RoundedCornerShape(24.dp)
    Card(
        shape = cardCornerShape,
        modifier = modifier
            .clip(cardCornerShape)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
            ),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(0.8f)
                    .padding(8.dp)
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp)
                        .wrapContentSize(Alignment.Center),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}