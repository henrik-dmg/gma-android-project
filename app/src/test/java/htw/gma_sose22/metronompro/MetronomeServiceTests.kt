package htw.gma_sose22.metronompro

import android.os.Build.VERSION_CODES.M
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
        MetronomeService.changeBPM(5)
        assertEquals(currentBPM + 5, MetronomeService.bpm)
    }

    private fun makeMetronome(): MetronomeInterface {
        val mockAudio = MockAudioTrack(100)
        return Metronome(80, ByteArray(100), mockAudio)
    }
}