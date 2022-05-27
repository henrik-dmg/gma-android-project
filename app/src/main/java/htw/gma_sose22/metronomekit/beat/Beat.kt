package htw.gma_sose22.metronomekit.beat

import kotlinx.serialization.Serializable

@Serializable
data class Beat(
    var tempo: Int?,
    var noteCount: Int?,
    var repetitions: Int?,
    var emphasisedNotes: IntArray,
    var mutedNotes: IntArray
) {

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
        var result = tempo ?: 0
        result = 31 * result + (noteCount ?: 0)
        result = 31 * result + (repetitions ?: 0)
        result = 31 * result + emphasisedNotes.contentHashCode()
        result = 31 * result + mutedNotes.contentHashCode()
        return result
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