package space.astar_technologies.app.persistence.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.astar_technologies.app.data.Rocket

@Entity(tableName = "Rockets")
data class RocketEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int?,
    val description: String,
    val liftoffThrust: Int?,
    val diameter: Int?,
    val status: String,
    val boosters: Int?,
    val imageUrl: String,
    val imageDescription: String,
    val parent: Int?
) {
    fun toModel(): Rocket = Rocket(id, name, height, description, liftoffThrust, diameter, status, boosters, imageUrl, imageDescription, parent)
}

fun Rocket.toEntity(): RocketEntity = RocketEntity(id, name, height, description, liftoffThrust, diameter, status, boosters, imageUrl, imageDescription, parent?.id)