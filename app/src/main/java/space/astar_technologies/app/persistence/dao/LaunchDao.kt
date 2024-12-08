package space.astar_technologies.app.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import space.astar_technologies.app.persistence.schema.LaunchEntity

@Dao
interface LaunchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(launch: LaunchEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(launches: List<LaunchEntity>)

    @Update
    suspend fun update(launch: LaunchEntity)

    @Delete
    suspend fun delete(launch: LaunchEntity)

    @Query("SELECT * FROM Launches WHERE id = :id")
    fun getLaunch(id: Int): Flow<LaunchEntity?>

    @Query("SELECT * FROM Launches ORDER BY date ASC")
    fun getAllLaunches(): Flow<List<LaunchEntity>>

    @Query("DELETE FROM Launches")
    fun clear()

    @Query("SELECT COUNT(*) FROM Launches")
    fun count(): Int

    fun isEmpty(): Boolean = count() == 0
}