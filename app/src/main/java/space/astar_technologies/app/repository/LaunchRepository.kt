package space.astar_technologies.app.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import space.astar_technologies.app.MainActivity
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.network.AstarService
import space.astar_technologies.app.persistence.AstarDatabase
import space.astar_technologies.app.persistence.dao.LaunchDao
import space.astar_technologies.app.persistence.schema.toEntity

class LaunchRepository(private val service: AstarService, context: Context) : Repository<Launch> {
    private val database: AstarDatabase = AstarDatabase.get(context)
    private val dao: LaunchDao by lazy { database.launchDao() }

    override suspend fun getAll(): List<Launch> = withContext(Dispatchers.IO) {
        try {
            val received: List<Launch> = service.getLaunches()

            if (received.isNotEmpty()) {
                MainActivity.isOffline = false
                dao.clear()
                dao.insert(received.map { it.toEntity() })
            } else MainActivity.isOffline = true

            dao.getAllLaunches().first().map { it.toModel(
                database.rocketDao().getRocket(it.rocket).first()!!.toModel()
            ) }
        } catch (exception: Exception) {
            MainActivity.isOffline = true

            dao.getAllLaunches().first().map { it.toModel(
                database.rocketDao().getRocket(it.rocket).first()!!.toModel()
            ) }
        }
    }

    override suspend fun getById(id: Int): Launch? = withContext(Dispatchers.IO) {
        if (dao.isEmpty()) getAll() // Populates the database

        dao.getLaunch(id).first()?.let { it.toModel(
            database.rocketDao().getRocket(it.rocket).first()!!.toModel()
        ) }
    }
}