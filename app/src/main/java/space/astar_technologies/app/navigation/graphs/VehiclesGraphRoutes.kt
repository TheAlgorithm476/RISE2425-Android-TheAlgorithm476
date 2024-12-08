package space.astar_technologies.app.navigation.graphs

import androidx.compose.material3.Text
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import space.astar_technologies.app.navigation.NavRoute
import space.astar_technologies.app.navigation.VehiclesNavRoute
import space.astar_technologies.app.ui.screens.vehicles.detail.VehicleDetail
import space.astar_technologies.app.ui.screens.vehicles.overview.VehicleOverview

fun NavGraphBuilder.addVehiclesRoutes(controller: NavController) {
    navigation(
        route = NavRoute.Vehicles.route,
        startDestination = VehiclesNavRoute.Overview.route
    ) {
        showVehiclesOverview(controller)
        showVehiclesDetail(controller)
    }
}

private fun NavGraphBuilder.showVehiclesOverview(controller: NavController) {
    composable(route = VehiclesNavRoute.Overview.route) {
        VehicleOverview({ controller.navigate(VehiclesNavRoute.Detail.createRoute(it)) })
    }
}

private fun NavGraphBuilder.showVehiclesDetail(controller: NavController) {
    composable(route = VehiclesNavRoute.Detail.route) { entry: NavBackStackEntry ->
        val id: Int = entry.arguments?.getString("id")?.toInt() ?: throw IllegalArgumentException("No Id provided!")
        VehicleDetail(id)
    }
}