package htw.gma_sose22.metronomekit.beat

import androidx.room.Entity
import androidx.room.PrimaryKey
import htw.gma_sose22.metronomekit.util.Validateable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.*

@Entity
@Serializable
data class BeatPattern(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var patternName: String = "New pattern",
    var beats: Array<Beat>,
    val createdAt: Instant = Clock.System.now()
) : Validateable {

    val numberOfBars: UInt?
        get() {
            var numberOfBars: UInt = 0u
            beats.forEach { beat ->
                if (beat.repetitions != null) {
                    numberOfBars += beat.repetitions!!
                } else {
                    return null
                }
            }
            return numberOfBars
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeatPattern

        if (patternName != other.patternName) return false
        if (createdAt != other.createdAt) return false
        if (!beats.contentEquals(other.beats)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = patternName.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + beats.contentHashCode()
        return result
    }

    override fun isValid(): Boolean {
        return beats.all { beat -> beat.isValid() }
    }

}