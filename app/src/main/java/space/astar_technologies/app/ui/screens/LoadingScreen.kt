package space.astar_technologies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Downloading
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.astar_technologies.app.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Outlined.Downloading,
            contentDescription = null,
            modifier = Modifier
                .scale(2f)
                .padding(16.dp)
        )
        Text(
            text = stringResource(R.string.action_loading_data),
            modifier = Modifier
                .padding(16.dp),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}

@Preview
@Composable
private fun LoadingScreen_Preview() {
    LoadingScreen()
}