package htw.gma_sose22.metronomprokit.metronome

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import htw.gma_sose22.metronomprokit.audio.AudioControllable
import htw.gma_sose22.metronomprokit.audio.AudioWriteable
import htw.gma_sose22.metronomprokit.audio.WrappedAudioTrack

object MetronomeService: AudioControllable {

    private val audioWriteable: MetronomeAudioInterface = makeAudioWriteable()
    private lateinit var metronome: MetronomeInterface

    fun configureMetronome(sound: ByteArray) {
        metronome = Metronome(40, sound, audioWriteable)
    }

    private fun makeAudioWriteable(): MetronomeAudioInterface {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val audioFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(44100)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(44100)
            .build()
        return WrappedAudioTrack(audioTrack)
    }

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