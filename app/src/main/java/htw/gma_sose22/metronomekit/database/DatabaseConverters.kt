package htw.gma_sose22.metronomekit.database

import androidx.room.TypeConverter
import htw.gma_sose22.metronomekit.beat.Beat
import kotlinx.datetime.Instant
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DatabaseConverters {

    @TypeConverter
    fun toBeatArray(jsonString: String): Array<Beat> {
        return Json.decodeFromString(jsonString)
    }

    @TypeConverter
    fun toBeatJsonArray(beatPatterns: Array<Beat>): String {
        return Json.encodeToString(beatPatterns)
    }

    @TypeConverter
    fun toInstantJson(instant: Instant): String {
        return Json.encodeToString(instant)
    }

    @TypeConverter
    fun toInstant(jsonString: String): Instant {
        return Json.decodeFromString(jsonString)
    }
}