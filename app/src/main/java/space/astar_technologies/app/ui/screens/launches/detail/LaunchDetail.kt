package space.astar_technologies.app.ui.screens.launches.detail

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.ui.components.DateText
import space.astar_technologies.app.ui.components.StatusPill
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen
import space.astar_technologies.app.ui.screens.vehicles.overview.VehicleCard

@Composable
fun LaunchDetail(
    id: Int,
    modifier: Modifier = Modifier
) {
    val vm: LaunchDetailViewModel = koinViewModel(parameters = { parametersOf(id) })

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(stringResource(R.string.error_not_connected), retryAction = { vm.fetchData(id) })
        is UiState.Success -> LaunchContents(state.launch, modifier)
    }
}

@Composable
fun LaunchContents(
    launch: Launch,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(
            text = launch.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                StatusPill(launch.success, Modifier.padding(bottom = 4.dp))
                DateText(launch.date, launch.dateIsNET)
            }
        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.launches_mission_details_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Text(
                text = launch.description,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.launches_mission_vehicle_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            VehicleCard(launch.rocket, {})
        }
    }
}