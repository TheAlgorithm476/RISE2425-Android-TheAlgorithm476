package space.astar_technologies.app.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import space.astar_technologies.app.data.serializers.LocalDateTimeSerializer

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val author: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("date_published") val datePublished: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("last_edited") val lastEdited: LocalDateTime,
    val content: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("image_description")
    val imageDescription: String,
    val rocketId: Int? = null,
    val rocket: Rocket? = null,
    val launchId: Int? = null,
    val launch: ArticleLaunch? = null
) {
    @Serializable
    data class ArticleLaunch(
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
        val rocket: ArticleRocket
    ) {
        fun toLaunch(rocket: Rocket): Launch = Launch(id, name, date, dateIsNET, description, success, imageUrl, imageDescription, rocket.id, rocket)
    }

    @Serializable
    data class ArticleRocket(
        val id: Int
    )
}