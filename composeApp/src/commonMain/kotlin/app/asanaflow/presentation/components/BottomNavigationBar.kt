package app.asanaflow.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.asanaflow.presentation.navigation.Destination
import asanaflow.composeapp.generated.resources.Res
import asanaflow.composeapp.generated.resources.bottom_bar_navigation_home
import asanaflow.composeapp.generated.resources.bottom_bar_navigation_settings
import asanaflow.composeapp.generated.resources.content_description_bottom_bar_navigation_home
import asanaflow.composeapp.generated.resources.content_description_bottom_bar_navigation_settings
import org.jetbrains.compose.resources.stringResource


@Composable
fun BottomNavigationBar(
    currentDestination: Destination?,
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier
): Unit = NavigationBar(modifier = modifier) {
    NavigationBarItem(
        icon = { Icon(Icons.Default.Home, stringResource(Res.string.content_description_bottom_bar_navigation_home)) },
        label = { Text(stringResource(Res.string.bottom_bar_navigation_home)) },
        selected = currentDestination == Destination.Home,
        onClick = { onNavigate(Destination.Home) }
    )
    NavigationBarItem(
        icon = { Icon(Icons.Default.Settings, stringResource(Res.string.content_description_bottom_bar_navigation_settings)) },
        label = { Text(stringResource(Res.string.bottom_bar_navigation_settings)) },
        selected = currentDestination == Destination.Settings,
        onClick = { onNavigate(Destination.Settings) }
    )
}
