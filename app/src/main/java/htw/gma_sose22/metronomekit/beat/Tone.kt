package htw.gma_sose22.metronomekit.beat

import kotlinx.serialization.Serializable

@Serializable
enum class Tone(val value: Int) {
    WOOD(1),
    CLICK(2),
    DING(3),
    BEEP(4);

    companion object {
        private val values = values()
    }

    fun next(): Tone {
        return values()[(this.ordinal + 1) % values.size]
    }
}