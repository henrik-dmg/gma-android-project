package htw.gma_sose22.metronomekit.beat

import kotlinx.serialization.Serializable

@Serializable
enum class Rhythm(val value: Int) {
    QUARTER(1),
    EIGHTH(2),
    SIXTEENTH(4);

    companion object {
        private val values = values()
    }

    fun next(): Rhythm {
        return values()[(this.ordinal + 1) % values.size]
    }
}