package htw.gma_sose22.metronompro

import htw.gma_sose22.metronomprokit.metronome.AudioGapCalculator
import org.junit.Test
import org.junit.Assert.*

class AudioGapCalculatorTests {

    @Test
    fun testGapHalfAsLongAsSound() {
        val bufferInfo = AudioGapCalculator.calculateBeatLength(60, 100, 50)
        assertEquals(50, bufferInfo.spaceLength)
    }

    @Test
    fun testGap70Sound50() {
        val bufferInfo = AudioGapCalculator.calculateBeatLength(120, 100, 40)
        assertEquals(10, bufferInfo.spaceLength)
    }

    @Test
    fun testSoundTooLong() {
        val bufferInfo = AudioGapCalculator.calculateBeatLength(80, 60, 50)
        // we expect 45, since the combination of sample rate and bpm results
        // in a maximum sound length of 45 samples
        assertEquals(45, bufferInfo.soundLength)
        assertEquals(0, bufferInfo.spaceLength)
    }

}