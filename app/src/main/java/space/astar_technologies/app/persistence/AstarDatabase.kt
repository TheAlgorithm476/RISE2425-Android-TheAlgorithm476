package space.astar_technologies.app.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.astar_technologies.app.persistence.dao.ArticleDao
import space.astar_technologies.app.persistence.dao.LaunchDao
import space.astar_technologies.app.persistence.dao.RocketDao
import space.astar_technologies.app.persistence.schema.ArticleEntity
import space.astar_technologies.app.persistence.schema.LaunchEntity
import space.astar_technologies.app.persistence.schema.RocketEntity

@Database(entities = [ RocketEntity::class, LaunchEntity::class, ArticleEntity::class ], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AstarDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketDao
    abstract fun launchDao(): LaunchDao
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: AstarDatabase? = null

        fun get(context: Context): AstarDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(context, AstarDatabase::class.java, "astar_database")
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}