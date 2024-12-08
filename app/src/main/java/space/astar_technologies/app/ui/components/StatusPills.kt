package space.astar_technologies.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.astar_technologies.app.R

@Composable
fun StatusPill(success: Boolean?, modifier: Modifier = Modifier) {
    val (color: Color, text: String) = when (success) {
        true -> Color.Green to stringResource(R.string.launches_status_success)
        false -> Color.Red to stringResource(R.string.launches_status_failure)
        else -> return
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            tint = color,
            contentDescription = null,
            modifier = Modifier.scale(0.75f)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun SuccessPill_Preview(modifier: Modifier = Modifier) {
    StatusPill(true)
}

@Preview
@Composable
fun FailurePill_Preview(modifier: Modifier = Modifier) {
    StatusPill(false)
}

@Preview
@Composable
fun UnknownPill_Preview(modifier: Modifier = Modifier) {
    // Should be an empty preview
    StatusPill(null)
}