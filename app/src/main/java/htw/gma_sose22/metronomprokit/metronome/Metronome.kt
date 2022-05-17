package htw.gma_sose22.metronomprokit.metronome

import android.util.Log
import java.util.*
import kotlin.math.roundToInt

class Metronome(
    override var bpm: Int,
    override var sound: ByteArray,
    override val metronomeAudio: MetronomeAudioInterface
) : MetronomeInterface {

    companion object {
        const val DEFAULT_SPEED = 100
    }

    override var isPlaying = false
        private set

    private var playbackRunnable: Runnable? = null
    private var playbackThread: Thread? = null

    override fun play() {
        metronomeAudio.play()
        isPlaying = true
        playbackRunnable = Runnable {
            while (isPlaying) {
                var beatLength = (60.0 / bpm * metronomeAudio.sampleRate).roundToInt()
                beatLength *= 2

                var soundLength = sound.size
                if (soundLength > beatLength) {
                    soundLength = beatLength
                } // with higher BPMs, the full sound is too long
                metronomeAudio.write(sound, 0, soundLength)
                val space = ByteArray(beatLength - soundLength)
                metronomeAudio.write(space, 0, space.size)
            }
        }
        playbackThread = Thread(playbackRunnable)
        playbackThread?.start()
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