package space.astar_technologies.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoute private constructor(val route: String, val title: String, val icon: ImageVector) {
    data object News : NavRoute("root_news", "News", Icons.Outlined.Newspaper)
    data object Launches : NavRoute("root_launches", "Launches", Icons.Outlined.RocketLaunch)
    data object Vehicles : NavRoute("root_vehicles", "Vehicles", Icons.Outlined.Rocket)

    companion object {
        val roots: List<NavRoute>
            get() = listOf(
                News,
                Launches,
                Vehicles
            )
    }
}

sealed class NewsNavRoute private constructor(val route: String) {
    data object Overview : NewsNavRoute("news_overview")

    data object Detail : NewsNavRoute("news_detail/{id}") {
        fun createRoute(id: Int): String = "news_detail/$id"
    }
}

sealed class LaunchesNavRoute private constructor(val route: String) {
    data object Overview : LaunchesNavRoute("launches_overview")

    data object Detail : LaunchesNavRoute("launches_detail/{id}") {
        fun createRoute(id: Int): String = "launches_detail/$id"
    }
}

sealed class VehiclesNavRoute private constructor(val route: String) {
    data object Overview : VehiclesNavRoute("vehicles_overview")

    data object Detail : VehiclesNavRoute("vehicles_detail/{id}") {
        fun createRoute(id: Int): String = "vehicles_detail/$id"
    }
}