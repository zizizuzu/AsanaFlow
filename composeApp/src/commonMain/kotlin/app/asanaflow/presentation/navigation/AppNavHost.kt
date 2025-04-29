package app.asanaflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.asanaflow.presentation.screens.schedule.HomeScreen
import app.asanaflow.presentation.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
): Unit = NavHost(
    navController = navController,
    startDestination = Destination.Home.baseRoute,
    modifier = modifier
) {
    composable(Destination.Home.baseRoute) {
        HomeScreen(
            openPreviewTrainingAction = {
                navController.navigate(Destination.PreviewTraining)
            }
        )
    }
    composable(Destination.Settings.baseRoute) {
        SettingsScreen()
    }
    composable(Destination.PreviewTraining.baseRoute) {
//        PreviewTrainingScreen(
//            onBack = { navController.popBackStack() }
//        )
    }
}