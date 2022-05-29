package htw.gma_sose22.metronomekit.beat

import htw.gma_sose22.metronomekit.util.DateSerializer
import htw.gma_sose22.metronomekit.util.Validateable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BeatPattern(
    var patternName: String?,
    @Serializable(DateSerializer::class)
    var createdAt: Date?,
    var beats: Array<Beat>
): Validateable {

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
        var result = patternName?.hashCode() ?: 0
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + beats.contentHashCode()
        return result
    }

    override fun isValid(): Boolean {
        return beats.all { beat -> beat.isValid() }
    }

}