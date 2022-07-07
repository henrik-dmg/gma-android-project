package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatException
import htw.gma_sose22.metronomekit.beat.Tone
import org.junit.Assert.*
import org.junit.Test

class BeatTests {

    @Test
    fun testSimpleMonotonousBeat() {
        val beat = Beat(120, 4u)
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        tones.forEach { tone ->
            assertEquals(Tone.regular, tone)
        }
    }

    @Test
    fun testSimpleAlternatingBeat() {
        val beat = Beat(120, 4u, null, setOf(0u, 2u), setOf())
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        assertEquals(Tone.emphasised, tones[0])
        assertEquals(Tone.regular, tones[1])
        assertEquals(Tone.emphasised, tones[2])
        assertEquals(Tone.regular, tones[3])
    }

    @Test
    fun testComplexBeat() {
        val beat = Beat(120, 4u, null, setOf(0u, 2u), setOf(1u))
        assertTrue(beat.isValid())

        val tones = beat.makeNotes()
        assertEquals(Tone.emphasised, tones[0])
        assertEquals(Tone.muted, tones[1])
        assertEquals(Tone.emphasised, tones[2])
        assertEquals(Tone.regular, tones[3])
    }

    @Test
    fun testComplexOverlappingBeat() {
        val beat = Beat(120, 4u, null, setOf(0u, 2u), setOf(1u, 2u))
        assertFalse(beat.isValid())
    }

    @Test
    fun testRotatingFirstNote() {
        val beat = Beat(120, 4u)
        assertTrue(beat.isValid())

        assertEquals(Tone.regular, beat.makeNotes()[0])
        beat.rotateNote(0u)
        assertEquals(Tone.muted, beat.makeNotes()[0])
        beat.rotateNote(0u)
        assertEquals(Tone.emphasised, beat.makeNotes()[0])
        beat.rotateNote(0u)
        assertEquals(Tone.regular, beat.makeNotes()[0])
    }

    @Test
    fun testRotatingFirstAndSecondNote() {
        val beat = Beat(120, 4u)
        assertTrue(beat.isValid())

        val initialTones = beat.makeNotes()
        initialTones.forEach { tone ->
            assertEquals(Tone.regular, tone)
        }

        beat.rotateNote(0u)
        beat.rotateNote(1u)

        val finalTones = beat.makeNotes()

        assertEquals(Tone.muted, finalTones[0])
        assertEquals(Tone.muted, finalTones[1])
        assertEquals(Tone.regular, finalTones[2])
        assertEquals(Tone.regular, finalTones[3])
    }

    @Test
    @Throws(BeatException::class)
    fun testTempoIsLessThanMinimum() {
        val beat = Beat(10, 4u)
        assertFalse(beat.isValid())
    }

    @Test
    @Throws(BeatException::class)
    fun testTempoIsGreaterThanMinimum() {
        val beat = Beat(210, 4u)
        assertFalse(beat.isValid())
    }

    @Test
    @Throws(BeatException::class)
    fun testHasAtLeastOneNote() {
        val beat = Beat(noteCount = 0u)
        assertFalse(beat.isValid())
    }

    @Test
    @Throws(BeatException::class)
    fun testHasTooManyNote() {
        val beat = Beat(noteCount = 12u)
        assertFalse(beat.isValid())
    }

}