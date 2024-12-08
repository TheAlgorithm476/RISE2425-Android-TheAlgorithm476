package space.astar_technologies.app.navigation.graphs

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import space.astar_technologies.app.navigation.NavRoute
import space.astar_technologies.app.navigation.NewsNavRoute
import space.astar_technologies.app.ui.screens.articles.detail.ArticleDetail
import space.astar_technologies.app.ui.screens.articles.overview.ArticlesOverview

fun NavGraphBuilder.addNewsRoutes(controller: NavController) {
    navigation(
        route = NavRoute.News.route,
        startDestination = NewsNavRoute.Overview.route
    ) {
        showNewsOverview(controller)
        showNewsDetail(controller)
    }
}

private fun NavGraphBuilder.showNewsOverview(controller: NavController) {
    composable(route = NewsNavRoute.Overview.route) {
        ArticlesOverview({ controller.navigate(NewsNavRoute.Detail.createRoute(it)) })
    }
}

private fun NavGraphBuilder.showNewsDetail(controller: NavController) {
    composable(route = NewsNavRoute.Detail.route) { entry: NavBackStackEntry ->
        val id: Int = entry.arguments?.getString("id")?.toInt() ?: throw IllegalArgumentException("No Id provided!")

        ArticleDetail(id)
    }
}