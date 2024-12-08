package space.astar_technologies.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import space.astar_technologies.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    behavior: TopAppBarScrollBehavior,
    controller: NavHostController,
    destination: NavDestination?,
    modifier: Modifier = Modifier
) {
    val bottomScreenNames: List<NavRoute> = NavRoute.roots
    val isBottom: Boolean =
        if (destination == null) true
        else destination.route in bottomScreenNames.map { it.route }

    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.astar_name)) },
        navigationIcon = {
            if (!isBottom) {
                IconButton(
                    onClick = { controller.popBackStack() },
                    modifier = modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.action_go_back),
                    )
                }
            }
        },
        scrollBehavior = behavior
    )
}