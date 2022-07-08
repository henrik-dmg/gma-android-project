package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.metronome.MetronomeService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MetronomeServiceTests {

    @Before
    fun setUp() {
        val mockAudio = MockAudioTrack(100)
        MetronomeService.configureMetronome(
            beatSound = ByteArray(100),
            offbeatSound = ByteArray(100),
            metronomeAudio = mockAudio
        )
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

}