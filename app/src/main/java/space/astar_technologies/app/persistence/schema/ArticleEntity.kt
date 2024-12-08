package space.astar_technologies.app.persistence.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.data.Rocket

@Entity(tableName = "Articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String,
    val datePublished: Instant,
    val lastEdited: Instant,
    val content: String,
    val imageUrl: String,
    val imageDescription: String,
    val rocket: Int?,
    val launch: Int?
) {
    fun toModel(rocket: Rocket? = null, launch: Article.ArticleLaunch? = null): Article = Article(id, title, author, datePublished.toLocalDateTime(TimeZone.currentSystemDefault()), lastEdited.toLocalDateTime(TimeZone.currentSystemDefault()), content, imageUrl, imageDescription, this.rocket, rocket, this.launch, launch)
}

fun Article.toEntity(): ArticleEntity = ArticleEntity(id, title, author, datePublished.toInstant(TimeZone.currentSystemDefault()), lastEdited.toInstant(TimeZone.currentSystemDefault()), content, imageUrl, imageDescription, rocketId, launchId)