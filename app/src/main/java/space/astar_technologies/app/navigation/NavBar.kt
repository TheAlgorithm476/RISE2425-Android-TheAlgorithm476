package space.astar_technologies.app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun NavBar(
    controller: NavHostController,
    selected: NavRoute,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        NavRoute.roots.forEach { root: NavRoute ->
            NavItem(root, selected, controller, modifier)
        }
    }
}

@Composable
fun RowScope.NavItem(
    route: NavRoute,
    selected: NavRoute,
    controller: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBarItem(
        selected = selected == route,
        label = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = route.title,
                    fontSize = (14.0f).sp
                )
            }
        },
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = route.icon,
                    contentDescription = null
                )
            }
        },
        onClick = {
            controller.navigate(route.route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(controller.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        },
        modifier = Modifier
            .wrapContentHeight()
    )
}