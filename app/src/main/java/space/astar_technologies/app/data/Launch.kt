package space.astar_technologies.app.data

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import space.astar_technologies.app.data.serializers.LocalDateTimeSerializer

@Serializable
data class Launch(
    val id: Int,
    val name: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime? = null,
    @SerialName("date_is_net") val dateIsNET: Boolean? = null,
    val description: String,
    val success: Boolean? = null,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("image_description")
    val imageDescription: String,
    val rocketId: Int = -1,
    val rocket: Rocket
) {
    fun isPast(): Boolean {
        if (success != null) return true
        if (date != null && date.toInstant(TimeZone.UTC) < Clock.System.now()) return true

        return false
    }
}