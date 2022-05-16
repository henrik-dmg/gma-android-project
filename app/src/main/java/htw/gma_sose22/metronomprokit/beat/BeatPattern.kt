package htw.gma_sose22.metronomprokit.beat

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class BeatPattern {
    @JsonProperty("patternName")
    var patternName: String? = null

    @JsonProperty("createdAt")
    var createdAt: Date? = null
}