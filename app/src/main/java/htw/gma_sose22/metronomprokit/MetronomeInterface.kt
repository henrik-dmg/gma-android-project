package htw.gma_sose22.metronomprokit

import android.media.AudioTrack

interface MetronomeInterface {
    val bpm: Int
    var sound: ByteArray
    val audioTrack: AudioTrack

    fun getIsPlaying() : Boolean

    fun start()
    fun stop()
    fun togglePlayback()
}