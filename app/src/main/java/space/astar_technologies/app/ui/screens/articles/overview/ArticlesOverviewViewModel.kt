package space.astar_technologies.app.ui.screens.articles.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import kotlinx.coroutines.launch
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.repository.Repository

sealed interface UiState {
    data class Success(val articles: List<Article>) : UiState
    data class Error(val string: String) : UiState
    data object Loading : UiState
}

class ArticlesOverviewViewModel(private val repository: Repository<Article>) : ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val result: List<Article> = repository.getAll()
                UiState.Success(result.sortedByDescending { it.datePublished })
            } catch (exception: HttpException) {
                UiState.Error("HTTP Error: ${exception.response.code}")
            } catch (exception: Exception) {
                UiState.Error("Something went wrong: ${exception.message}")
            }
        }
    }
}