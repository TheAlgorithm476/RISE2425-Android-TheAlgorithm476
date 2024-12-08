package space.astar_technologies.app.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.ArticleDao
import space.astar_technologies.app.persistence.schema.ArticleEntity
import space.astar_technologies.app.persistence.schema.toEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    private lateinit var dao: ArticleDao
    private lateinit var database: AstarDatabase

    @Before
    fun before() {
        val context: Context = ApplicationProvider.getApplicationContext()

        this.database = Room.inMemoryDatabaseBuilder(context, AstarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        this.dao = database.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun after() = database.close()

    private suspend fun addOne(article: Article) = dao.insert(article.toEntity())
    private suspend fun addMany(vararg articles: Article) = dao.insert(articles.map { it.toEntity() }.toList())

    @Test
    @Throws(Exception::class)
    fun `ArticleDao -- Insert 1 Article`() = runBlocking {
        addOne(MockDataSource.articles.first())

        val all: List<ArticleEntity> = dao.getAllArticles().first()
        Assert.assertEquals(all.first(), MockDataSource.articles.first().toEntity())
    }

    @Test
    @Throws(Exception::class)
    fun `ArticleDao -- Insert all Articles`() = runBlocking {
        addMany(*MockDataSource.articles.toTypedArray())

        val all: List<ArticleEntity> = dao.getAllArticles().first()

        Assert.assertEquals(all.size, MockDataSource.articles.size)
        for (i in all.indices) Assert.assertEquals(all[i], MockDataSource.articles[i].toEntity())
    }
}