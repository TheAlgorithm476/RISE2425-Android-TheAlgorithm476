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
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.LaunchDao
import space.astar_technologies.app.persistence.schema.LaunchEntity
import space.astar_technologies.app.persistence.schema.toEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LaunchDaoTest {
    private lateinit var dao: LaunchDao
    private lateinit var database: AstarDatabase

    @Before
    fun before() {
        val context: Context = ApplicationProvider.getApplicationContext()

        this.database = Room.inMemoryDatabaseBuilder(context, AstarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        this.dao = database.launchDao()
    }

    @After
    @Throws(IOException::class)
    fun after() = database.close()

    private suspend fun addOne(launch: Launch) = dao.insert(launch.toEntity())
    private suspend fun addMany(vararg launches: Launch) = dao.insert(launches.map { it.toEntity() }.toList())

    @Test
    @Throws(Exception::class)
    fun `LaunchDao -- Insert 1 Launch`() = runBlocking {
        addOne(MockDataSource.launches.first())

        val all: List<LaunchEntity> = dao.getAllLaunches().first()
        Assert.assertEquals(all.first(), MockDataSource.launches.first().toEntity())
    }

    @Test
    @Throws(Exception::class)
    fun `LaunchDao -- Insert all Launches`() = runBlocking {
        addMany(*MockDataSource.launches.toTypedArray())

        val all: List<LaunchEntity> = dao.getAllLaunches().first()
        // LaunchEntities are sorted by date when fetched
        val mock: List<LaunchEntity> = MockDataSource.launches.sortedBy { it.date }.map { it.toEntity() }

        Assert.assertEquals(all.size, MockDataSource.launches.size)
        for (i in all.indices) Assert.assertEquals(all[i], mock[i])
    }
}