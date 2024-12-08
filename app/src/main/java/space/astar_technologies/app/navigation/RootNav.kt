package space.astar_technologies.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import space.astar_technologies.app.navigation.graphs.addLaunchesRoutes
import space.astar_technologies.app.navigation.graphs.addNewsRoutes
import space.astar_technologies.app.navigation.graphs.addVehiclesRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavController(
    controller: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val selected: NavRoute by controller.currentScreenAsState()
    val currentRoute: String? by controller.currentRouteAsState()

    val behavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val entry: NavBackStackEntry? by controller.currentBackStackEntryAsState()
    val destination: NavDestination? = entry?.destination

    Scaffold(
        topBar = {
            if (currentRoute != null && !(currentRoute!!.contains("root") || currentRoute!!.contains("overview")))
                TopBar(behavior, controller, destination)
        },
        bottomBar = {
            if (currentRoute != null && (currentRoute!!.contains("root") || currentRoute!!.contains("overview")))
                NavBar(controller = controller, selected = selected)
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding: PaddingValues ->
        RootNavGraph(
            controller = controller,
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + dynamicPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + dynamicPadding()
                )
        )
    }
}

@Composable
fun RootNavGraph(
    controller: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = controller,
        startDestination = NavRoute.News.route,
        modifier = modifier.fillMaxSize()
    ) {
        addNewsRoutes(controller)
        addLaunchesRoutes(controller)
        addVehiclesRoutes(controller)
    }
}

@Composable
private fun NavController.currentScreenAsState(): State<NavRoute> {
    val selected: MutableState<NavRoute> = remember { mutableStateOf(NavRoute.Launches) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination: NavDestination, _ ->
            selected.value = when {
                destination.hierarchy.any { it.route == NavRoute.News.route } -> NavRoute.News
                destination.hierarchy.any { it.route == NavRoute.Launches.route } -> NavRoute.Launches
                destination.hierarchy.any { it.route == NavRoute.Vehicles.route } -> NavRoute.Vehicles

                else -> error("Illegal Navigation State!")
            }
        }

        addOnDestinationChangedListener(listener)
        onDispose { removeOnDestinationChangedListener(listener) }
    }

    return selected
}

@Composable
private fun NavController.currentRouteAsState(): State<String?> {
    val selected: MutableState<String?> = remember { mutableStateOf(null) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination: NavDestination, _ ->
            selected.value = destination.route
        }

        addOnDestinationChangedListener(listener)
        onDispose { removeOnDestinationChangedListener(listener) }
    }

    return selected
}

@Composable
private fun dynamicPadding(): Dp {
    val width: Int = LocalConfiguration.current.screenWidthDp
    return if (width < 600) 0.dp else if (width < 1200) 96.dp else 192.dp
}