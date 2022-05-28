package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatManager
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomekit.beat.Tone
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class BeatManagerTests {

    @Test
    fun testSimpleBeat() {
        val beats = Array<Beat>(1) {
            Beat(120,4, 1, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        val beatManager = BeatManager()
        beatManager.loadBeat(beatPattern)

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(null, beatManager.nextTone())
    }

    @Test
    fun testRepeatingBeat() {
        val beats = Array<Beat>(1) {
            Beat(120,3, 2, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        val beatManager = BeatManager()
        beatManager.loadBeat(beatPattern)

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(null, beatManager.nextTone())
    }

    @Test
    fun testInfiniteBeat() {
        val beats = Array<Beat>(1) {
            Beat(120,4, null, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        val beatManager = BeatManager()
        beatManager.loadBeat(beatPattern)

        for (i in 0..100) {
            assertEquals(Tone.regular, beatManager.nextTone())
        }
    }

}