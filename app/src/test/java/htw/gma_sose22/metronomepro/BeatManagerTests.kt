package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class BeatManagerTests {

    @Before
    fun setup() {
        BeatManager.reset()
    }

    @Test
    fun testSimpleBeat() {
        val beats = arrayOf(Beat(120, 4u, 1u))
        val beatPattern = BeatPattern(beats = beats)
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
            Beat(120, 4u, 1u, setOf(1u, 3u), setOf())
        }
        val beatPattern = BeatPattern(beats = beats)
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
            Beat(120, 4u, 1u, setOf(1u, 3u), setOf()),
            Beat(130, 3u, 2u, setOf(2u), setOf())
        )
        val beatPattern = BeatPattern(beats = beats)
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
            Beat(120, 3u, 2u)
        }
        val beatPattern = BeatPattern(beats = beats)
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
            Beat(120, 4u, null)
        }
        val beatPattern = BeatPattern(beats = beats)
        BeatManager.loadBeatPattern(beatPattern)

        for (i in 0..100) {
            assertEquals(Tone.regular, BeatManager.nextTone())
        }
    }

    @Test
    fun testInvalidBeat() {
        val beats = Array(1) {
            Beat(120, 2u, 1u, setOf(3u, 4u), setOf())
        }
        val beatPattern = BeatPattern(beats = beats)
        assertThrows(BeatManagerException::class.java) {
            BeatManager.loadBeatPattern(beatPattern)
        }
    }

}