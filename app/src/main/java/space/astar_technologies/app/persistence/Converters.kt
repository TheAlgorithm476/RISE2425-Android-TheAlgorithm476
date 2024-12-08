package space.astar_technologies.app.persistence

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class Converters {
    @TypeConverter
    fun fromInstant(value: Long?): Instant? = value?.let { Instant.fromEpochMilliseconds(it) }

    @TypeConverter
    fun toInstant(instant: Instant?): Long? = instant?.toEpochMilliseconds()
}