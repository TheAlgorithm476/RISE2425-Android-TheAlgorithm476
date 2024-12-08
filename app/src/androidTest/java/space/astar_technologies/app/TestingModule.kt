package space.astar_technologies.app

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import space.astar_technologies.app.mock.MockArticleRepository
import space.astar_technologies.app.mock.MockLaunchRepository
import space.astar_technologies.app.mock.MockRocketRepository
import space.astar_technologies.app.ui.screens.articles.detail.ArticleDetailViewModel
import space.astar_technologies.app.ui.screens.articles.overview.ArticlesOverviewViewModel
import space.astar_technologies.app.ui.screens.launches.detail.LaunchDetailViewModel
import space.astar_technologies.app.ui.screens.launches.overview.LaunchOverviewViewModel
import space.astar_technologies.app.ui.screens.vehicles.detail.VehicleDetailViewModel
import space.astar_technologies.app.ui.screens.vehicles.overview.VehicleOverviewViewModel

val testingModule: Module = module {
    viewModel { LaunchOverviewViewModel(MockLaunchRepository()) }
    viewModel { ArticlesOverviewViewModel(MockArticleRepository()) }
    viewModel { VehicleOverviewViewModel(MockRocketRepository()) }

    viewModel { (id: Int) -> ArticleDetailViewModel(id, MockArticleRepository()) }
    viewModel { (id: Int) -> LaunchDetailViewModel(id, MockLaunchRepository()) }
    viewModel { (id: Int) -> VehicleDetailViewModel(id, MockRocketRepository()) }
}