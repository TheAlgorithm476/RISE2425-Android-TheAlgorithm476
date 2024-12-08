package space.astar_technologies.app.ui.screens.vehicles.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import org.koin.core.parameter.parametersOf
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.ui.components.VehicleStatusLabel
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen

@Composable
fun VehicleDetail(
    id: Int,
    modifier: Modifier = Modifier
) {
    val vm: VehicleDetailViewModel = koinViewModel(parameters = { parametersOf(id) })

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(stringResource(R.string.error_not_connected), retryAction = { vm.fetchData(id) })
        is UiState.Success -> VehicleContents(state.vehicle, modifier)
    }
}

@Composable
fun VehicleContents(
    vehicle: Rocket,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vehicle.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = vehicle.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    VehicleStatusLabel(vehicle.status)
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = vehicle.description,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(8.dp)) {
                    vehicle.height?.let { Text(stringResource(R.string.vehicles_parameter_height, it)) }
                    vehicle.liftoffThrust?.let { Text(stringResource(R.string.vehicles_parameter_liftoff_thrust, it)) }
                    vehicle.diameter?.let { Text(stringResource(R.string.vehicles_parameter_diameter, it)) }
                    vehicle.boosters?.let { Text(stringResource(R.string.vehicles_parameter_boosters, it)) }
                }
            }
        }
    }
}