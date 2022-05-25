package htw.gma_sose22.metronomekit.beat

import com.fasterxml.jackson.annotation.JsonProperty

class Beat {
    @JsonProperty("tempo")
    var tempo: Int? = null

    @JsonProperty("noteValue")
    var noteValue: Int? = null

    @JsonProperty("noteCount")
    var noteCount: Int? = null

    @JsonProperty("repetitions")
    var repetitions: Int? = null

    @JsonProperty("emphasisedNotes")
    lateinit var emphasisedNotes: Array<Int>

    @JsonProperty("mutedNotes")
    lateinit var mutedNotes: Array<Int>
}