package htw.gma_sose22.metronomprokit.audio

import android.media.AudioTrack
import htw.gma_sose22.metronomprokit.metronome.MetronomeAudioInterface

class WrappedAudioTrack(private val audioTrack: AudioTrack): MetronomeAudioInterface {

    override val sampleRate: Int
        get() = audioTrack.sampleRate

    override fun write(audioData: ByteArray, offsetInBytes: Int, sizeInBytes: Int): Int {
        return audioTrack.write(audioData, offsetInBytes, sizeInBytes)
    }

    override fun play() {
        audioTrack.play()
    }

    override fun stop() {
        audioTrack.pause()
        audioTrack.flush()
    }

}