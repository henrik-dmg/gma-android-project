package htw.gma_sose22.metronomekit.metronome

import htw.gma_sose22.metronomekit.audio.AudioControllable

object MetronomeService : AudioControllable {

    lateinit var metronome: MetronomeInterface

    var bpm: Int
        get() = metronome.bpm
        set(value) {
            metronome.bpm = value
        }

    override val isPlaying: Boolean
        get() = metronome.isPlaying

    override fun play() {
        metronome.play()
    }

    override fun stop() {
        metronome.stop()
    }

    override fun togglePlayback() {
        metronome.togglePlayback()
    }

}