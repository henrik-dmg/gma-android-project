package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatPattern
import org.junit.Assert.assertEquals
import org.junit.Test

class BeatPatternTests {

    @Test
    fun testCorrectBarsCount() {
        val beat = Beat(repetitions = 4u, noteCount = 17u)
        val pattern = BeatPattern(beats = arrayOf(beat))
        assertEquals(4u, pattern.numberOfBars)
    }

}