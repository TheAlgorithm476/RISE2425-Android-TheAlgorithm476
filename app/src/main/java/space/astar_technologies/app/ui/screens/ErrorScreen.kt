package space.astar_technologies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Button
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
fun ErrorScreen(
    message: String,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Outlined.CloudOff,
            contentDescription = null,
            modifier = Modifier
                .scale(2f)
                .padding(16.dp)
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.action_retry))
        }
    }
}

@Preview
@Composable
private fun ErrorScreen_Preview() {
    ErrorScreen(stringResource(R.string.error_not_connected), {})
}