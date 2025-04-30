package app.asanaflow.presentation.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.asanaflow.data.local.datastore.ThemeDataStore
import app.asanaflow.domain.model.ThemeMode
import app.asanaflow.presentation.navigation.AppNavHost
import app.asanaflow.presentation.navigation.Destination
import app.asanaflow.presentation.navigation.navigateTo
import app.asanaflow.presentation.theme.AsanaFlowTheme
import app.asanaflow.ui.components.BottomNavigationBar
import co.touchlab.kermit.Logger
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    val logger = remember { Logger.withTag("MainScreen") }
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
                logger.e { "🚨 Unsupported destination: $currentRoute" }
            }

            Destination.Home
        }
    }
    val theme by koinInject<ThemeDataStore>().themeModeFlow.collectAsStateWithLifecycle(ThemeMode.AUTO)

    AsanaFlowTheme(themeMode = theme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
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