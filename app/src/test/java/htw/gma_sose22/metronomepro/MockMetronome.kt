package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.beat.Tone
import htw.gma_sose22.metronomekit.metronome.MetronomeAudioInterface
import htw.gma_sose22.metronomekit.metronome.MetronomeInterface

class MockMetronome(
    override var bpm: Int,
    override var beatSound: ByteArray,
    override var offbeatSound: ByteArray,
    override val metronomeAudio: MetronomeAudioInterface,
    override val nextToneClosure: () -> Tone?
) : MetronomeInterface {

    override var isPlaying = false
        private set

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