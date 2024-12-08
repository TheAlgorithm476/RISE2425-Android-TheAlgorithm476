package space.astar_technologies.app.persistence.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.data.Rocket

@Entity(tableName = "Launches")
data class LaunchEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val date: Instant?,
    val dateIsNET: Boolean?,
    val description: String,
    val success: Boolean?,
    val imageUrl: String,
    val imageDescription: String,
    val rocket: Int
) {
    fun toModel(rocket: Rocket): Launch = Launch(id, name, date?.toLocalDateTime(TimeZone.currentSystemDefault()), dateIsNET, description, success, imageUrl, imageDescription, this.rocket, rocket)
}

fun Launch.toEntity(): LaunchEntity = LaunchEntity(id, name, date?.toInstant(TimeZone.currentSystemDefault()), dateIsNET, description, success, imageUrl, imageDescription, rocket.id)