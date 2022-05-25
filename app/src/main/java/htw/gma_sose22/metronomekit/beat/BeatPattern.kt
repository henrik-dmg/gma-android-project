package htw.gma_sose22.metronomekit.beat

import htw.gma_sose22.metronomekit.util.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BeatPattern(
    var patternName: String?,
    @Serializable(DateSerializer::class)
    var createdAt: Date?
)