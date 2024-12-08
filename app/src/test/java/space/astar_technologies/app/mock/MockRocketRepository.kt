package space.astar_technologies.app.mock

import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.repository.Repository

class MockRocketRepository : Repository<Rocket> {
    override suspend fun getAll(): List<Rocket> = MockDataSource.rockets
    override suspend fun getById(id: Int): Rocket? = MockDataSource.rockets.find { it.id == id }
}