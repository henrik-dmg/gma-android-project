package htw.gma_sose22.metronomprokit.metronome

import kotlin.math.roundToInt

object AudioGapCalculator {

    data class BufferInfo(val beatLength: Int, val soundLength: Int, val spaceLength: Int)

    fun calculateBeatLength(bpm: Int, sampleRate: Int, soundLength: Int): BufferInfo {
        val beatLength = (60.0 / bpm * sampleRate).roundToInt()
        return if (beatLength < soundLength) {
            BufferInfo(beatLength, beatLength, 0)
        } else {
            BufferInfo(beatLength, soundLength, beatLength - soundLength)
        }
    }

}