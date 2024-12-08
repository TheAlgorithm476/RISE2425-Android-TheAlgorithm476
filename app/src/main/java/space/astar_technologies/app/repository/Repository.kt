package space.astar_technologies.app.repository

interface Repository<T> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: Int): T?
}