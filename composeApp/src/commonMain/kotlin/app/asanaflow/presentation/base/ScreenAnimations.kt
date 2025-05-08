package app.asanaflow.presentation.base

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import app.asanaflow.presentation.theme.AnimationDurations


fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromRight() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(AnimationDurations.ScreenTransitionAnimationDuration)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToLeft() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(AnimationDurations.ScreenTransitionAnimationDuration)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromLeft() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(AnimationDurations.ScreenTransitionAnimationDuration)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToRight() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(AnimationDurations.ScreenTransitionAnimationDuration)
)

fun Modifier.slideFromBottom(visible: Boolean): Modifier = composed {
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 150f,
        animationSpec = tween(300)
    )
    this.offset(y = offsetY.dp)
}