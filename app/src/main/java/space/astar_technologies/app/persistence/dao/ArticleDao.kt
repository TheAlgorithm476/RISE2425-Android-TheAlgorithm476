package space.astar_technologies.app.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import space.astar_technologies.app.persistence.schema.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articles: List<ArticleEntity>)

    @Update
    suspend fun update(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM Articles WHERE id = :id")
    fun getArticle(id: Int): Flow<ArticleEntity?>

    @Query("SELECT * FROM Articles ORDER BY datePublished ASC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM Articles")
    fun clear()

    @Query("SELECT COUNT(*) FROM Articles")
    fun count(): Int

    fun isEmpty(): Boolean = count() == 0
}