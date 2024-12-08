package space.astar_technologies.app.tests.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.mock.MockRocketRepository
import space.astar_technologies.app.repository.Repository
import space.astar_technologies.app.rules.TestDispatcherRule

class RocketRepositoryTests {
    private val repository: Repository<Rocket> = MockRocketRepository()

    @get:Rule
    val dispatcher: TestRule = TestDispatcherRule()

    @Test
    fun `Rocket Repository -- Get All Rockets`() = runTest {
        Assert.assertEquals(MockDataSource.rockets, repository.getAll())
    }

    @Test
    fun `Rocket Repository -- Get 1 Rocket (valid)`() = runTest {
        Assert.assertEquals(MockDataSource.rockets.find { it.id == 1 }, repository.getById(1))
    }

    @Test
    fun `Rocket Repository -- Get 1 Rocket (invalid)`() = runTest {
        Assert.assertEquals(MockDataSource.rockets.find { it.id == 10 }, repository.getById(10))
    }
}