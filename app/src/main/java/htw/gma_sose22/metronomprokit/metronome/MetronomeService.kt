package htw.gma_sose22.metronomprokit.metronome

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import htw.gma_sose22.metronomprokit.audio.AudioControllable
import htw.gma_sose22.metronomprokit.audio.WrappedAudioTrack

object MetronomeService: AudioControllable {

    lateinit var metronome: MetronomeInterface

    val bpm: Int
        get() = metronome.bpm

    override fun getIsPlaying(): Boolean {
        return metronome.getIsPlaying()
    }

    override fun play() {
        metronome.play()
    }

    override fun stop() {
        metronome.stop()
    }

    override fun togglePlayback() {
        metronome.togglePlayback()
    }

    fun changeBPM(bpmDelta: Int) {
        metronome.bpm += bpmDelta
    }

}