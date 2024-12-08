package space.astar_technologies.app.mock

import space.astar_technologies.app.data.Article
import space.astar_technologies.app.repository.Repository

class MockArticleRepository : Repository<Article> {
    override suspend fun getAll(): List<Article> = MockDataSource.articles
    override suspend fun getById(id: Int): Article? = MockDataSource.articles.find { it.id == id }
}