package htw.gma_sose22.metronompro

import htw.gma_sose22.metronomprokit.metronome.Metronome
import htw.gma_sose22.metronomprokit.metronome.MetronomeInterface
import htw.gma_sose22.metronomprokit.metronome.MetronomeService
import org.junit.Test
import org.junit.Assert.*


class MetronomeServiceTests {

    @Test
    fun testBPMChange() {
        val metronome = makeMetronome()
        MetronomeService.metronome = metronome

        val currentBPM = MetronomeService.bpm
        MetronomeService.bpm += 5
        assertEquals(currentBPM + 5, MetronomeService.bpm)
    }

    @Test
    fun testNegativeBPMChange() {
        val metronome = makeMetronome()
        MetronomeService.metronome = metronome

        val currentBPM = MetronomeService.bpm
        MetronomeService.bpm -= 5
        assertEquals(currentBPM - 5, MetronomeService.bpm)
    }

    @Test
    fun testStartAndStopPlayback() {
        val metronome = makeMetronome()
        MetronomeService.metronome = metronome

        assertFalse(MetronomeService.isPlaying)
        MetronomeService.play()
        assertTrue(MetronomeService.isPlaying)
        MetronomeService.stop()
        assertFalse(MetronomeService.isPlaying)
    }

    @Test
    fun testTogglePlayback() {
        val metronome = makeMetronome()
        MetronomeService.metronome = metronome

        assertFalse(MetronomeService.isPlaying)
        MetronomeService.togglePlayback()
        assertTrue(MetronomeService.isPlaying)
        MetronomeService.togglePlayback()
        assertFalse(MetronomeService.isPlaying)
    }

    private fun makeMetronome(): MetronomeInterface {
        val mockAudio = MockAudioTrack(100)
        return Metronome(80, ByteArray(100), mockAudio)
    }

}