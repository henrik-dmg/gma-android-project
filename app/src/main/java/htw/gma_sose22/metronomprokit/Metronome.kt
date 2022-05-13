package htw.gma_sose22.metronomprokit

import android.media.AudioTrack
import kotlin.math.roundToInt

class Metronome(
    override val bpm: Int,
    override var sound: ByteArray,
    override val audioTrack: AudioTrack) : MetronomeInterface {

    companion object {
        const val DEFAULT_SPEED = 100
    }

    private var isPlaying = false
    private var playbackRunnable: Runnable? = null
    private var playbackThread: Thread? = null

    override fun getIsPlaying(): Boolean {
        return isPlaying
    }

    private fun buildSpace(beatLength: Int, soundLength: Int): ByteArray {
        val error = 0
        val spaceLength = beatLength - soundLength + error
        return ByteArray(spaceLength)
    }

    override fun start() {
        audioTrack.play()
        isPlaying = true
        playbackRunnable = Runnable {
            while (isPlaying) {
                var beatLength = (60.0 / bpm * audioTrack.sampleRate).roundToInt()
                beatLength *= 2
                var soundLength = sound.size
                if (soundLength > beatLength) {
                    soundLength = beatLength
                } // with higher BPMs, the full sound is too long
                audioTrack.write(sound, 0, soundLength)
                val space = buildSpace(beatLength, soundLength)
                audioTrack.write(space, 0, space.size)
            }
        }
        playbackThread = Thread(playbackRunnable)
        playbackThread?.start()
    }

    override fun stop() {
        audioTrack.pause()
        audioTrack.flush()
        isPlaying = false
    }

    override fun togglePlayback() {
        if (isPlaying) {
            stop()
        } else {
            start()
        }
    }

}