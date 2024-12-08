package space.astar_technologies.app.ui.screens.vehicles.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.koin.androidx.compose.koinViewModel
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.ui.components.OfflineNotice
import space.astar_technologies.app.ui.components.VehicleStatusLabel
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen

@Composable
fun VehicleOverview(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: VehicleOverviewViewModel = koinViewModel()

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(message = stringResource(R.string.error_not_connected), retryAction = { vm.fetchData() })
        is UiState.Success -> VehiclesList(
            when (vm.selected) {
                SelectedType.Families -> state.families
                SelectedType.Configurations -> state.configurations
                SelectedType.TestArticles -> state.testArticles
            }, vm, navigateToDetail, modifier)
    }
}

@Composable
fun VehiclesList(
    rockets: List<Rocket>,
    vm: VehicleOverviewViewModel,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (MainActivity.isOffline) {
            OfflineNotice({ vm.fetchData() })
        }

        Text(
            text = stringResource(R.string.vehicles_name_plural),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            SelectedType.entries.forEachIndexed { index: Int, it: SelectedType ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = SelectedType.entries.size),
                    selected = it == vm.selected,
                    onClick = { vm.selected = it },
                    icon = {}
                ) {
                    Text(stringResource(it.displayName))
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rockets, key = { it.id }) {
                VehicleCard(it, navigateToDetail)
            }
        }
    }
}

@Composable
fun VehicleCard(
    vehicle: Rocket,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16.0f / 9.0f)
            .clickable { navigateToDetail(vehicle.id) }
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vehicle.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.vehicles_photo_title, vehicle.name),
                modifier = Modifier.weight(1.0f)
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp)
            ) {
                Text(
                    text = vehicle.name,
                    style = MaterialTheme.typography.titleLarge
                )
                VehicleStatusLabel(vehicle.status)

                vehicle.height?.let { Text(stringResource(R.string.vehicles_parameter_height, it)) }
                vehicle.liftoffThrust?.let { Text(stringResource(R.string.vehicles_parameter_liftoff_thrust, it)) }
                vehicle.diameter?.let { Text(stringResource(R.string.vehicles_parameter_diameter, it)) }
                vehicle.boosters?.let { Text(stringResource(R.string.vehicles_parameter_boosters, it)) }
            }
        }
    }
}