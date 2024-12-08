package space.astar_technologies.app.mock

import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.repository.Repository

class MockLaunchRepository : Repository<Launch> {
    override suspend fun getAll(): List<Launch> = MockDataSource.launches
    override suspend fun getById(id: Int): Launch? = MockDataSource.launches.find { it.id == id }
}