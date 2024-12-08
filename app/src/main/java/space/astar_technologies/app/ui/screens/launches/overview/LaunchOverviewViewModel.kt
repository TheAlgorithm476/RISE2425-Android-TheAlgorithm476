package space.astar_technologies.app.ui.screens.launches.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import kotlinx.coroutines.launch
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.repository.Repository

enum class SelectedTimePeriod {
    Past,
    Future;
}

sealed interface UiState {
    data class Success(val past: List<Launch>, val future: List<Launch>) : UiState
    data class Error(val string: String) : UiState
    data object Loading : UiState
}

class LaunchOverviewViewModel(private val repository: Repository<Launch>) : ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set
    var selectedTimePeriod: SelectedTimePeriod by mutableStateOf(SelectedTimePeriod.Future)

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val result: List<Launch> = repository.getAll()
                UiState.Success(
                    result.filter { it.isPast() }.sortedByDescending { it.date },
                    result.filter { !it.isPast() }.sortedByDescending { it.date })
            } catch (exception: HttpException) {
                UiState.Error("HTTP Error: ${exception.response.code}")
            } catch (exception: Exception) {
                UiState.Error("Something went wrong: ${exception.message}")
            }
        }
    }
}