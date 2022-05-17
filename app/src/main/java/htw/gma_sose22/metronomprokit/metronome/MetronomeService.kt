package htw.gma_sose22.metronomprokit.metronome

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import htw.gma_sose22.metronomprokit.audio.AudioControllable
import htw.gma_sose22.metronomprokit.audio.WrappedAudioTrack

object MetronomeService: AudioControllable {

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