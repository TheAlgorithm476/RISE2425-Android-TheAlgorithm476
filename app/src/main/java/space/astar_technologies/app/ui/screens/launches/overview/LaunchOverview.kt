package space.astar_technologies.app.ui.screens.launches.overview

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.R
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.ui.components.DateText
import space.astar_technologies.app.ui.components.OfflineNotice
import space.astar_technologies.app.ui.components.StatusPill
import space.astar_technologies.app.ui.components.TimeToLaunchText
import space.astar_technologies.app.ui.screens.ErrorScreen
import space.astar_technologies.app.ui.screens.LoadingScreen

@Composable
fun LaunchOverview(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: LaunchOverviewViewModel = koinViewModel()

    when (val state: UiState = vm.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(message = stringResource(R.string.error_not_connected), retryAction = { vm.fetchData() })
        is UiState.Success -> LaunchList(state.past, state.future, vm, navigateToDetail)
    }
}

@Composable
fun LaunchList(
    pastLaunches: List<Launch>,
    futureLaunches: List<Launch>,
    vm: LaunchOverviewViewModel,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (MainActivity.isOffline) {
            OfflineNotice({ vm.fetchData() })
        }

        Text(
            text = stringResource(R.string.launches_name_plural),
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
            SelectedTimePeriod.entries.forEachIndexed { index: Int, it: SelectedTimePeriod ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = SelectedTimePeriod.entries.size),
                    selected = it == vm.selectedTimePeriod,
                    onClick = { vm.selectedTimePeriod = it },
                    icon = {}
                ) {
                    Text(it.name)
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(if (vm.selectedTimePeriod == SelectedTimePeriod.Future) futureLaunches else pastLaunches, key = { it.id }) { launch: Launch ->
                LaunchCard(launch, navigateToDetail)
            }
        }
    }
}

@Composable
fun LaunchCard(
    launch: Launch,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16.0f / 9.0f)
            .clickable { navigateToDetail(launch.id) }
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(launch.rocket.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = launch.rocket.imageDescription,
                modifier = Modifier.weight(1.0f)
            )
            Column(
                modifier = Modifier
                    .weight(2.5f)
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.weight(1.0f)) {
                    StatusPill(
                        success = launch.success,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                    Text(
                        text = launch.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(launch.rocket.name)
                    DateText(launch.date, launch.dateIsNET)
                }
                Column {
                    TimeToLaunchText(launch.date, launch.dateIsNET)
                }
            }
        }
    }
}

@Preview
@Composable
fun LaunchCard_Preview() {
    LaunchCard(launch = Launch(
        id = 1,
        dateIsNET = false,
        date = LocalDateTime.parse("2024-09-28T13:00:00"),
        name = "TF-1",
        description = "First flight of any A-1 rocket. First launch organised by Astar.\\n\\nAfter liftoff, heavy winds caught the rocket, and drastically changed its course, causing it to crash into the ground seconds later. All primary objectives of the mission were achieved however.",
        success = true,
        rocket = Rocket(
            id = 6,
            liftoffThrust = 44,
            name = "Mk3 Prototype A-1",
            boosters = 3,
            description = "The Mk3 Prototype A-1 was the first A-1 model that finished construction, and the first model to perform a flight test. It replaced Mk2's PLA body with a strong ABS body, as well as a more integrated inter-tank structure.\n\nMk3 performed the first flight test of an A-1 rocket.",
            height = 120,
            status = "Expended",
            diameter = 10,
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 in flight"
        ),
        imageUrl = "",
        imageDescription = ""
    ), {})
}