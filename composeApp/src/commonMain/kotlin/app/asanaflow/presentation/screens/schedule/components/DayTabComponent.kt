package app.asanaflow.presentation.screens.schedule.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import app.asanaflow.presentation.screens.schedule.model.DayType
import app.asanaflow.presentation.theme.AnimationDurations
import app.asanaflow.presentation.theme.Dimens

@Composable
fun DayTabComponent(
    type: DayType,
    isSelected: Boolean,
    dayOfMonth: Int,
    dayOfWeek: String,
    onClick: () -> Unit
) {
    val annotatedText = remember(dayOfMonth, dayOfWeek) {
        buildAnnotatedString {
            withStyle(SpanStyle(fontSize = Dimens.DayTabMonthTextSize)) { append("$dayOfMonth\n") }
            withStyle(SpanStyle(fontSize = Dimens.DayTabWeekTextSize)) { append(dayOfWeek) }
        }
    }

    val bgColor = DayTabStyles.bgColor(type, isSelected)
    val textColor = DayTabStyles.textColor(type, isSelected)

    val animatedBgColor by animateColorAsState(
        targetValue = bgColor,
        animationSpec = tween(durationMillis = AnimationDurations.TabAnimationDuration)
    )

    val animatedTextColor by animateColorAsState(
        targetValue = textColor,
        animationSpec = tween(durationMillis = AnimationDurations.TabAnimationDuration)
    )

    Text(
        modifier = Modifier.tabModifier(type, isSelected, animatedBgColor, onClick),
        text = annotatedText,
        color = animatedTextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Modifier.tabModifier(
    type: DayType,
    isSelected: Boolean,
    bgColor: Color,
    onClick: () -> Unit
): Modifier = composed {
    this.width(Dimens.DayTabWidth)
        .clip(Dimens.DayTabShape)
        .clickable(
            enabled = type != DayType.FUTURE,
            onClick = onClick
        )
        .then(
            if (!isSelected && type != DayType.FUTURE) {
                Modifier.border(Dimens.BorderWidth, Color.LightGray, CircleShape)
            } else {
                Modifier
            }
        )
        .background(color = bgColor, shape = Dimens.DayTabShape)
        .padding(vertical = Dimens.DayTabVerticalPadding)
}