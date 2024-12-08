package space.astar_technologies.app.navigation.graphs

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import space.astar_technologies.app.navigation.LaunchesNavRoute
import space.astar_technologies.app.navigation.NavRoute
import space.astar_technologies.app.ui.screens.launches.detail.LaunchDetail
import space.astar_technologies.app.ui.screens.launches.overview.LaunchOverview

fun NavGraphBuilder.addLaunchesRoutes(controller: NavController) {
    navigation(
        route = NavRoute.Launches.route,
        startDestination = LaunchesNavRoute.Overview.route
    ) {
        showLaunchesOverview(controller)
        showLaunchesDetail(controller)
    }
}

private fun NavGraphBuilder.showLaunchesOverview(controller: NavController) {
    composable(route = LaunchesNavRoute.Overview.route) {
        LaunchOverview({ controller.navigate(LaunchesNavRoute.Detail.createRoute(it)) })
    }
}

private fun NavGraphBuilder.showLaunchesDetail(controller: NavController) {
    composable(route = LaunchesNavRoute.Detail.route) { entry: NavBackStackEntry ->
        val id: Int = entry.arguments?.getString("id")?.toInt() ?: throw IllegalArgumentException("No Id provided!")

        LaunchDetail(id)
    }
}