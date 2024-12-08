package space.astar_technologies.app.ui.screens.vehicles.overview

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import kotlinx.coroutines.launch
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.repository.Repository

enum class SelectedType(@StringRes val displayName: Int) {
    Families(R.string.enums_vehicles_families),
    Configurations(R.string.enums_vehicles_configurations),
    TestArticles(R.string.enums_vehicles_test_articles);
}

sealed interface UiState {
    data class Success(val families: List<Rocket>, val configurations: List<Rocket>, val testArticles: List<Rocket>) :
        UiState
    data class Error(val error: String) : UiState
    data object Loading : UiState
}

class VehicleOverviewViewModel(private val repository: Repository<Rocket>) : ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set
    var selected: SelectedType by mutableStateOf(SelectedType.Families)

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val result: List<Rocket> = repository.getAll().sortedByDescending { it.id }
                UiState.Success(
                    result.filter { it.isFamily() },
                    result.filter { it.isConfiguration() },
                    result.filter { it.isTestArticle() })
            } catch (exception: HttpException) {
                UiState.Error("HTTP Error: ${exception.response.code}")
            } catch (exception: Exception) {
                UiState.Error("Something went wrong: ${exception.message}")
            }
        }
    }
}