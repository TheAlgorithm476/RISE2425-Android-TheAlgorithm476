package space.astar_technologies.app.ui.screens.articles.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.ui.screens.launches.overview.LaunchCard
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen
import space.astar_technologies.app.ui.screens.vehicles.overview.VehicleCard

@Composable
fun ArticleDetail(
    id: Int,
    modifier: Modifier = Modifier
) {
    val vm: ArticleDetailViewModel = koinViewModel(parameters = { parametersOf(id) })

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(stringResource(R.string.error_not_connected), retryAction = { vm.fetchData(id) })
        is UiState.Success -> ArticleContents(state.article, modifier)
    }
}

@Composable
fun ReferenceCard(name: String, content: @Composable () -> Unit) {
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = stringResource(R.string.articles_description_reference, name), style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(4.dp))
            content()
        }
    }
}

@Composable
fun ArticleContents(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        if (article.rocket != null) {
            ReferenceCard(article.rocket.name) {
                VehicleCard(article.rocket, {}, modifier = Modifier.padding(4.dp))
            }
        }
        if (article.launch != null) {
            ReferenceCard(article.launch.name) {
                LaunchCard(article.launch.toLaunch(article.rocket!!), {}, modifier = Modifier.padding(4.dp))
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp,bottom = 32.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(text = "Published ${article.datePublished.date} by ${article.author}")
            if (article.datePublished != article.lastEdited) Text(
                text = "Last Edited: ${article.lastEdited.date}",
                fontStyle = FontStyle.Italic
            )
        }
        MarkdownText(
            markdown = article.content,
            style = TextStyle(
                textAlign = TextAlign.Center
            )
        )
    }
}