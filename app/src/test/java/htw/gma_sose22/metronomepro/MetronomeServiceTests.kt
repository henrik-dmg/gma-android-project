package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.metronome.Metronome
import htw.gma_sose22.metronomekit.metronome.MetronomeInterface
import htw.gma_sose22.metronomekit.metronome.MetronomeService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MetronomeServiceTests {

    @Before
    fun setUp() {
        val metronome = makeMetronome()
        MetronomeService.metronome = metronome
    }

    @Test
    fun testBPMChange() {
        val currentBPM = MetronomeService.bpm
        MetronomeService.bpm += 5
        assertEquals(currentBPM + 5, MetronomeService.bpm)
    }

    @Test
    fun testNegativeBPMChange() {
        val currentBPM = MetronomeService.bpm
        MetronomeService.bpm -= 5
        assertEquals(currentBPM - 5, MetronomeService.bpm)
    }

    @Test
    fun testStartAndStopPlayback() {
        assertFalse(MetronomeService.isPlaying)
        MetronomeService.play()
        assertTrue(MetronomeService.isPlaying)
        MetronomeService.stop()
        assertFalse(MetronomeService.isPlaying)
    }

    @Test
    fun testTogglePlayback() {
        assertFalse(MetronomeService.isPlaying)
        MetronomeService.togglePlayback()
        assertTrue(MetronomeService.isPlaying)
        MetronomeService.togglePlayback()
        assertFalse(MetronomeService.isPlaying)
    }

    private fun makeMetronome(): MetronomeInterface {
        val mockAudio = MockAudioTrack(100)
        return Metronome(80, ByteArray(100), ByteArray(80), mockAudio)
    }

}