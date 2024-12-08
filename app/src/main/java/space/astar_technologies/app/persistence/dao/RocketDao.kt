package space.astar_technologies.app.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import space.astar_technologies.app.persistence.schema.RocketEntity

@Dao
interface RocketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rocket: RocketEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rockets: List<RocketEntity>)

    @Update
    suspend fun update(rocket: RocketEntity)

    @Delete
    suspend fun delete(rocket: RocketEntity)

    @Query("SELECT * FROM Rockets WHERE id = :id")
    fun getRocket(id: Int): Flow<RocketEntity?>

    @Query("SELECT * FROM Rockets ORDER BY name ASC")
    fun getAllRockets(): Flow<List<RocketEntity>>

    @Query("DELETE FROM Rockets")
    fun clear()

    @Query("SELECT COUNT(*) FROM Rockets")
    fun count(): Int

    fun isEmpty(): Boolean = count() == 0
}