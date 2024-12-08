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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VehicleStatusLabel(status: String, modifier: Modifier = Modifier) {
    val color: Color = when (status) {
        "Active" -> Color.Green
        "Expended" -> Color(255, 150, 0)
        "Retired" -> Color.Gray
        "Destroyed" -> Color.Red
        "Planned" -> Color.Cyan
        else -> Color.Black
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
            text = status,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun ActiveStatus_Preview() {
    VehicleStatusLabel("Active")
}

@Preview
@Composable
private fun ExpendedStatus_Preview() {
    VehicleStatusLabel("Expended")
}

@Preview
@Composable
private fun RetiredStatus_Preview() {
    VehicleStatusLabel("Retired")
}

@Preview
@Composable
private fun DestroyedStatus_Preview() {
    VehicleStatusLabel("Destroyed")
}

@Preview
@Composable
private fun PlannedStatus_Preview() {
    VehicleStatusLabel("Planned")
}