package space.astar_technologies.app.ui.screens.articles.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.koin.androidx.compose.koinViewModel
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.ui.components.OfflineNotice
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen

@Composable
fun ArticlesOverview(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: ArticlesOverviewViewModel = koinViewModel()

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(stringResource(R.string.error_not_connected), retryAction = { vm.fetchData() })
        is UiState.Success -> ArticlesList(vm, state.articles, navigateToDetail)
    }
}

@Composable
fun ArticlesList(
    vm: ArticlesOverviewViewModel,
    articles: List<Article>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        if (MainActivity.isOffline) {
            OfflineNotice({ vm.fetchData() })
        }

        Text(
            text = stringResource(R.string.articles_name_plural),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articles, key = { it.id }) { article: Article ->
                ArticleCard(article, navigateToDetail)
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(4.0f / 3.0f)
            .clickable { navigateToDetail(article.id) }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.weight(1.0f)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = stringResource(R.string.articles_author, article.author, article.datePublished.date),
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}