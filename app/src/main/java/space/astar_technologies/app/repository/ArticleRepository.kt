package space.astar_technologies.app.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.network.AstarService
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.ArticleDao
import space.astar_technologies.app.persistence.schema.ArticleEntity
import space.astar_technologies.app.persistence.schema.toEntity

class ArticleRepository(private val service: AstarService, context: Context) : Repository<Article> {
    private val database: AstarDatabase = AstarDatabase.get(context)
    private val dao: ArticleDao by lazy { database.articleDao() }

    override suspend fun getAll(): List<Article> = withContext(Dispatchers.IO) {
        try {
            val received: List<Article> = service.getArticles()

            if (received.isNotEmpty()) {
                MainActivity.isOffline = false
                dao.clear()
                dao.insert(received.map { it.toEntity() })
            } else MainActivity.isOffline = true

            dao.getAllArticles().first().map { articleEntity: ArticleEntity ->
                articleEntity.toModel(database.rocketDao().getRocket(articleEntity.rocket ?: -1).first()?.toModel(), database.launchDao().getLaunch(articleEntity.launch ?: -1).first()?.let {
                Article.ArticleLaunch(it.id, it.name, it.date?.toLocalDateTime(TimeZone.currentSystemDefault()), it.dateIsNET, it.description, it.success, it.imageUrl, it.imageDescription, Article.ArticleRocket(it.rocket))
            })}
        } catch (exception: Exception) {
            MainActivity.isOffline = true

            dao.getAllArticles().first().map { articleEntity: ArticleEntity ->
                articleEntity.toModel(database.rocketDao().getRocket(articleEntity.rocket ?: -1).first()?.toModel(), database.launchDao().getLaunch(articleEntity.launch ?: -1).first()?.let {
                    Article.ArticleLaunch(it.id, it.name, it.date?.toLocalDateTime(TimeZone.currentSystemDefault()), it.dateIsNET, it.description, it.success, it.imageUrl, it.imageDescription, Article.ArticleRocket(it.rocket))
                })}
        }
    }

    override suspend fun getById(id: Int): Article? = service.getArticle(id)
}