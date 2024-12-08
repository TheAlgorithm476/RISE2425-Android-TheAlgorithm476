package space.astar_technologies.app.tests.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.mock.MockDataSource
import space.astar_technologies.app.mock.MockLaunchRepository
import space.astar_technologies.app.repository.Repository
import space.astar_technologies.app.rules.TestDispatcherRule

class LaunchRepositoryTests {
    private val repository: Repository<Launch> = MockLaunchRepository()

    @get:Rule
    val dispatcher: TestRule = TestDispatcherRule()

    @Test
    fun `Launch Repository -- Get All Launches`() = runTest {
        Assert.assertEquals(MockDataSource.launches, repository.getAll())
    }

    @Test
    fun `Launch Repository -- Get 1 Launch (valid)`() = runTest {
        Assert.assertEquals(MockDataSource.launches.find { it.id == 1 }, repository.getById(1))
    }

    @Test
    fun `Launch Repository -- Get 1 Launch (invalid)`() = runTest {
        Assert.assertEquals(MockDataSource.launches.find { it.id == 10 }, repository.getById(10))
    }
}