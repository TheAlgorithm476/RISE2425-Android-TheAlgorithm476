package space.astar_technologies.app.ui.screens.articles.detail

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import kotlinx.coroutines.launch
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.repository.Repository

sealed interface UiState {
    data class Success(val article: Article) : UiState
    data class Error(val message: String) : UiState
    data object Loading : UiState
}

class ArticleDetailViewModel(private val id: Int, private val repository: Repository<Article>) : ViewModel() {
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        fetchData(id)
    }

    fun fetchData(id: Int) {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val result: Article? = repository.getById(id)

                if (result == null) UiState.Error(
                    Resources.getSystem().getString(R.string.error_not_found_article)
                )
                else UiState.Success(result)
            } catch (exception: HttpException) {
                UiState.Error("HTTP Error: ${exception.response.code}")
            } catch (exception: Exception) {
                UiState.Error("Something went wrong: ${exception.message}")
            }
        }
    }
}