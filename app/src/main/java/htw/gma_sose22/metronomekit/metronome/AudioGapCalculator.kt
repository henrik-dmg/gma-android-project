package htw.gma_sose22.metronomekit.metronome

import kotlin.math.roundToInt

object AudioGapCalculator {

    data class BufferInfo(val beatLength: Int, val soundLength: Int, val spaceLength: Int)

    fun calculateBeatLength(bpm: Int, sampleRate: Int, soundLength: Int): BufferInfo {
        val beatLength = (60.0 / bpm * sampleRate).roundToInt()
        // If BPM is too high, sound length will be longer than beat length
        // for these cases we need to return a spaceLength of 0
        return if (beatLength < soundLength) {
            BufferInfo(beatLength, beatLength, 0)
        } else {
            BufferInfo(beatLength, soundLength, beatLength - soundLength)
        }
    }

}