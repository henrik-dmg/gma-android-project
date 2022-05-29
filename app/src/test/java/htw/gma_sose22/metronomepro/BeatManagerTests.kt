package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import java.util.*

class BeatManagerTests {

    private val beatManager = BeatManager()

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
        beatManager.reset()
    }

    @Test
    fun testSimpleBeat() {
        val beats = Array(1) {
            Beat(120, 4, 1, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        beatManager.loadBeat(beatPattern)

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(null, beatManager.nextTone())
    }

    @Test
    fun testSimpleAlternatingBeat() {
        val beats = Array(1) {
            Beat(120,4, 1, intArrayOf(1, 3), null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        beatManager.loadBeat(beatPattern)

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())
        assertEquals(null, beatManager.nextTone())
    }

    @Test
    fun testTwoBeats() {
        val beats = arrayOf(
            Beat(120,4, 1, intArrayOf(1, 3), null),
            Beat(130,3, 2, intArrayOf(2), null)
        )
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        beatManager.loadBeat(beatPattern)

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())

        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.regular, beatManager.nextTone())
        assertEquals(Tone.emphasised, beatManager.nextTone())

        assertEquals(null, beatManager.nextTone())
    }

    @Test
    fun testRepeatingBeat() {
        val beats = Array(1) {
            Beat(120,3, 2, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
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
        val beats = Array(1) {
            Beat(120,4, null, null, null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        beatManager.loadBeat(beatPattern)

        for (i in 0..100) {
            assertEquals(Tone.regular, beatManager.nextTone())
        }
    }

    @Test
    fun testInvalidBeat() {
        val beats = Array(1) {
            Beat(120, 2, 1, intArrayOf(3, 4), null)
        }
        val beatPattern = BeatPattern("Pattern", Date(), beats)
        assertThrows(BeatManagerException::class.java) {
            beatManager.loadBeat(beatPattern)
        }
    }

}