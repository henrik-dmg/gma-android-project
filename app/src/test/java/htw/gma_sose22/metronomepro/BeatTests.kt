package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.Tone
import org.junit.Assert.*
import org.junit.Test

class BeatTests {

    @Test
    fun testSimpleMonotonousBeat() {
        val beat = Beat(120, 4, null)
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        tones.forEach { tone ->
            assertEquals(Tone.regular, tone)
        }
    }

    @Test
    fun testSimpleAlternatingBeat() {
        val beat = Beat(120, 4, null, setOf(0, 2), setOf())
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        assertEquals(Tone.emphasised, tones[0])
        assertEquals(Tone.regular, tones[1])
        assertEquals(Tone.emphasised, tones[2])
        assertEquals(Tone.regular, tones[3])
    }

    @Test
    fun testComplexBeat() {
        val beat = Beat(120, 4, null, setOf(0, 2), setOf(1))
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        assertEquals(Tone.emphasised, tones[0])
        assertEquals(Tone.muted, tones[1])
        assertEquals(Tone.emphasised, tones[2])
        assertEquals(Tone.regular, tones[3])
    }

    @Test
    fun testComplexOverlappingBeat() {
        val beat = Beat(120, 4, null, setOf(0, 2), setOf(1, 2))
        assertFalse(beat.isValid())
    }

}