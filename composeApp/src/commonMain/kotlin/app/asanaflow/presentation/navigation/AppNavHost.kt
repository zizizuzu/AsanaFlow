package app.asanaflow.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.asanaflow.di.scopes.ScheduleScope
import app.asanaflow.presentation.base.slideInFromLeft
import app.asanaflow.presentation.base.slideInFromRight
import app.asanaflow.presentation.base.slideOutToLeft
import app.asanaflow.presentation.base.slideOutToRight
import app.asanaflow.presentation.screens.schedule.ScheduleScreen
import app.asanaflow.presentation.screens.schedule.ScheduleViewModel
import app.asanaflow.presentation.screens.settings.SettingsScreen
import app.asanaflow.presentation.screens.training.preview.PreviewTrainingScreen
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.qualifier.named

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
): Unit = NavHost(
    navController = navController,
    startDestination = Destination.Home.baseRoute,
    modifier = modifier
) {
    composable(
        route = Destination.Home.baseRoute,
        enterTransition = {
            val initialState = initialState.destination.route

            when (initialState) {
                Destination.PreviewTraining.baseRoute -> fadeIn()
                Destination.Settings.baseRoute -> slideInFromRight()
                else -> null
            }
        },
        exitTransition = {
            val targetState = targetState.destination.route

            when (targetState) {
                Destination.PreviewTraining.baseRoute -> fadeOut()
                Destination.Settings.baseRoute -> slideOutToLeft()

                else -> null
            }
        }) {
        val koin: Koin = getKoin()
        val scope = remember { koin.getOrCreateScope("scheduleScope", named<ScheduleScope>()) }
        val viewModel: ScheduleViewModel = scope.get()
        ScheduleScreen(
            viewModel = viewModel,
            openPreviewTrainingAction = {
                navController.navigate(Destination.PreviewTraining.buildRoute())
            }
        )
    }
    composable(
        route = Destination.Settings.baseRoute,
        enterTransition = { slideInFromLeft() },
        exitTransition = { slideOutToRight() }
    ) {
        SettingsScreen()
    }
    composable(
        route = Destination.PreviewTraining.baseRoute
    ) {
        PreviewTrainingScreen(
            onBack = { navController.popBackStack() }
        )
    }
}