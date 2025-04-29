package app.asanaflow.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.asanaflow.presentation.navigation.AppNavHost
import app.asanaflow.presentation.navigation.Destination
import app.asanaflow.presentation.navigation.navigateTo
import app.asanaflow.presentation.theme.AsanaFlowTheme
import app.asanaflow.ui.components.BottomNavigationBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MainScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destination.Home.baseRoute
    val currentDestination = when (currentRoute) {
        Destination.Home.buildRoute() -> Destination.Home
        Destination.Settings.buildRoute() -> Destination.Settings
        else -> {
            if (currentRoute !in setOf(
                    Destination.Home.baseRoute,
                    Destination.Settings.baseRoute,
                )
            ) {
//                Log.e("Navigation", "🚨 Unsupported destination: $currentRoute")
            }

            Destination.Home
        }
    }

    AsanaFlowTheme {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            bottomBar = {
                if (currentDestination in setOf(Destination.Home, Destination.Settings)) {
                    BottomNavigationBar(
                        currentDestination = currentDestination,
                        onNavigate = { destination -> navController.navigateTo(destination) }
                    )
                }
            }
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )

        }
    }
}