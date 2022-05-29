package htw.gma_sose22.metronomekit.beat

import htw.gma_sose22.metronomekit.util.Validateable
import kotlinx.serialization.Serializable

@Serializable
data class Beat(
    var tempo: Int,
    var noteCount: Int,
    var repetitions: Int?,
    var emphasisedNotes: IntArray?,
    var mutedNotes: IntArray?
): Validateable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Beat

        if (tempo != other.tempo) return false
        if (noteCount != other.noteCount) return false
        if (repetitions != other.repetitions) return false
        if (!emphasisedNotes.contentEquals(other.emphasisedNotes)) return false
        if (!mutedNotes.contentEquals(other.mutedNotes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tempo
        result = 31 * result + (noteCount)
        result = 31 * result + (repetitions ?: 0)
        result = 31 * result + emphasisedNotes.contentHashCode()
        result = 31 * result + mutedNotes.contentHashCode()
        return result
    }

    override fun isValid(): Boolean {
        emphasisedNotes?.forEach { index ->
            if (index >= noteCount) {
                return false
            }
        }
        mutedNotes?.forEach { index ->
            if (index >= noteCount) {
                return false
            }
        }
        return true
    }

    fun makeNotes(): Array<Tone> {
        val notes = Array(noteCount) { Tone.regular }
        emphasisedNotes?.forEach { index ->
            notes[index] = Tone.emphasised
        }
        mutedNotes?.forEach { index ->
            notes[index] = Tone.muted
        }
        return notes
    }

}

//class Beat {
//    @JsonProperty("tempo")
//    var tempo: Int? = null
//
//    @JsonProperty("noteValue")
//    var noteValue: Int? = null
//
//    @JsonProperty("noteCount")
//    var noteCount: Int? = null
//
//    @JsonProperty("repetitions")
//    var repetitions: Int? = null
//
//    @JsonProperty("emphasisedNotes")
//    lateinit var emphasisedNotes: Array<Int>
//
//    @JsonProperty("mutedNotes")
//    lateinit var mutedNotes: Array<Int>
//}