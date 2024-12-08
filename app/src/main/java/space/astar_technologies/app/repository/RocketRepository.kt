package space.astar_technologies.app.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.data.Rocket
import space.astar_technologies.app.network.AstarService
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.RocketDao
import space.astar_technologies.app.persistence.schema.toEntity

class RocketRepository(private val service: AstarService, context: Context) : Repository<Rocket> {
    private val database: AstarDatabase = AstarDatabase.get(context)
    private val dao: RocketDao by lazy { database.rocketDao() }

    override suspend fun getAll(): List<Rocket> = withContext(Dispatchers.IO) {
        try {
            val received: List<Rocket> = service.getRockets()

            if (received.isNotEmpty()) {
                MainActivity.isOffline = false
                dao.clear()
                dao.insert(received.map { it.toEntity() })
            } else MainActivity.isOffline = true

            dao.getAllRockets().first().map { it.toModel() }
        } catch (exception: Exception) {
            MainActivity.isOffline = true
            dao.getAllRockets().first().map { it.toModel() }
        }
    }

    override suspend fun getById(id: Int): Rocket? = withContext(Dispatchers.IO) {
        if (dao.isEmpty()) getAll() // Populates the database
        dao.getRocket(id).first()?.toModel()
    }
}