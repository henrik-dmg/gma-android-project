package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.util.*

class BeatManagerTests {

    @Before
    fun setup() {
        BeatManager.reset()
    }

    @Test
    fun testSimpleBeat() {
        val beats = Array(1) {
            Beat(120, 4, 1)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        BeatManager.loadBeatPattern(beatPattern)

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(null, BeatManager.nextTone())
    }

    @Test
    fun testSimpleAlternatingBeat() {
        val beats = Array(1) {
            Beat(120,4, 1, setOf(1, 3), setOf())
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        BeatManager.loadBeatPattern(beatPattern)

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())
        assertEquals(null, BeatManager.nextTone())
    }

    @Test
    fun testTwoBeats() {
        val beats = arrayOf(
            Beat(120,4, 1, setOf(1, 3), setOf()),
            Beat(130,3, 2, setOf(2), setOf())
        )
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        BeatManager.loadBeatPattern(beatPattern)

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.emphasised, BeatManager.nextTone())

        assertEquals(null, BeatManager.nextTone())
    }

    @Test
    fun testRepeatingBeat() {
        val beats = Array(1) {
            Beat(120,3, 2)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        BeatManager.loadBeatPattern(beatPattern)

        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(Tone.regular, BeatManager.nextTone())
        assertEquals(null, BeatManager.nextTone())
    }

    @Test
    fun testInfiniteBeat() {
        val beats = Array(1) {
            Beat(120,4, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        BeatManager.loadBeatPattern(beatPattern)

        for (i in 0..100) {
            assertEquals(Tone.regular, BeatManager.nextTone())
        }
    }

    @Test
    fun testInvalidBeat() {
        val beats = Array(1) {
            Beat(120, 2, 1, setOf(3, 4), setOf())
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        assertThrows(BeatManagerException::class.java) {
            BeatManager.loadBeatPattern(beatPattern)
        }
    }

}