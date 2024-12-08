package space.astar_technologies.app.tests.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.mock.MockArticleRepository
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.repository.Repository
import space.astar_technologies.app.rules.TestDispatcherRule

class ArticleRepositoryTests {
    private val repository: Repository<Article> = MockArticleRepository()

    @get:Rule
    val dispatcher: TestRule = TestDispatcherRule()

    @Test
    fun `Article Repository -- Get All Articles`() = runTest {
        Assert.assertEquals(MockDataSource.articles, repository.getAll())
    }

    @Test
    fun `Article Repository -- Get 1 Article (valid)`() = runTest {
        Assert.assertEquals(MockDataSource.articles.find { it.id == 1 }, repository.getById(1))
    }

    @Test
    fun `Article Repository -- Get 1 Article (invalid)`() = runTest {
        Assert.assertEquals(MockDataSource.articles.find { it.id == 10 }, repository.getById(10))
    }
}