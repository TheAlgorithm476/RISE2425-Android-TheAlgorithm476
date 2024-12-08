package space.astar_technologies.app.network

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import space.astar_technologies.app.repository.ArticleRepository
import space.astar_technologies.app.repository.LaunchRepository
import space.astar_technologies.app.repository.RocketRepository
import space.astar_technologies.app.ui.screens.articles.detail.ArticleDetailViewModel
import space.astar_technologies.app.ui.screens.articles.overview.ArticlesOverviewViewModel
import space.astar_technologies.app.ui.screens.launches.detail.LaunchDetailViewModel
import space.astar_technologies.app.ui.screens.launches.overview.LaunchOverviewViewModel
import space.astar_technologies.app.ui.screens.vehicles.detail.VehicleDetailViewModel
import space.astar_technologies.app.ui.screens.vehicles.overview.VehicleOverviewViewModel

val NETWORK_MODULE = module {
    single { "https://astar-technologies.space/" }
    single { AstarApi(get()) }
    single<AstarService> { get<AstarApi>().service }

    viewModel { LaunchOverviewViewModel(LaunchRepository(get(), get())) }
    viewModel { ArticlesOverviewViewModel(ArticleRepository(get(), get())) }
    viewModel { VehicleOverviewViewModel(RocketRepository(get(), get())) }

    viewModel { (id: Int) -> ArticleDetailViewModel(id, ArticleRepository(get(), get())) }
    viewModel { (id: Int) -> LaunchDetailViewModel(id, LaunchRepository(get(), get())) }
    viewModel { (id: Int) -> VehicleDetailViewModel(id, RocketRepository(get(), get())) }
}