package space.astar_technologies.app.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rocket(
    val id: Int,
    val name: String,
    val height: Int? = null,
    val description: String,
    @SerialName("liftoff_thrust") val liftoffThrust: Int? = null,
    val diameter: Int? = null,
    val status: String,
    val boosters: Int? = null,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("image_description")
    val imageDescription: String,
    val parentId: Int? = null,
    val parent: Rocket? = null
) {
    fun isFamily(): Boolean = this.parentId == null
    fun isConfiguration(): Boolean = !isFamily() && (this.name.contains("Prototype") || this.name.contains("Vehicle"))
    fun isTestArticle(): Boolean = !isFamily() && !isConfiguration()
}