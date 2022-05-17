package htw.gma_sose22.metronompro

import htw.gma_sose22.metronomprokit.metronome.MetronomeAudioInterface
import htw.gma_sose22.metronomprokit.metronome.MetronomeInterface

class MockMetronome(
    override var bpm: Int,
    override var sound: ByteArray,
    override val metronomeAudio: MetronomeAudioInterface
) : MetronomeInterface {

    var isPlaying = false

    override fun getIsPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun play() {
        isPlaying = true
        metronomeAudio.play()
    }

    override fun stop() {
        metronomeAudio.stop()
        isPlaying = false
    }

    override fun togglePlayback() {
        if (isPlaying) {
            stop()
        } else {
            play()
        }
    }

}