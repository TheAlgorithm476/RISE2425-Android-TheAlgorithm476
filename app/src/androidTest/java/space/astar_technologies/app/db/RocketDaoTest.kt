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
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.RocketDao
import space.astar_technologies.app.persistence.schema.RocketEntity
import space.astar_technologies.app.persistence.schema.toEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RocketDaoTest {
    private lateinit var dao: RocketDao
    private lateinit var database: AstarDatabase

    @Before
    fun before() {
        val context: Context = ApplicationProvider.getApplicationContext()

        this.database = Room.inMemoryDatabaseBuilder(context, AstarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        this.dao = database.rocketDao()
    }

    @After
    @Throws(IOException::class)
    fun after() = database.close()

    private suspend fun addOne(rocket: Rocket) = dao.insert(rocket.toEntity())
    private suspend fun addMany(vararg rockets: Rocket) = dao.insert(rockets.map { it.toEntity() }.toList())

    @Test
    @Throws(Exception::class)
    fun `RocketDao -- Insert 1 Rocket`() = runBlocking {
        addOne(MockDataSource.rockets.first())

        val all: List<RocketEntity> = dao.getAllRockets().first()
        Assert.assertEquals(all.first(), MockDataSource.rockets.first().toEntity())
    }

    @Test
    @Throws(Exception::class)
    fun `RocketDao -- Insert all Rockets`() = runBlocking {
        addMany(*MockDataSource.rockets.toTypedArray())

        val all: List<RocketEntity> = dao.getAllRockets().first()

        Assert.assertEquals(all.size, MockDataSource.rockets.size)
        for (i in all.indices) Assert.assertEquals(all[i], MockDataSource.rockets[i].toEntity())
    }
}