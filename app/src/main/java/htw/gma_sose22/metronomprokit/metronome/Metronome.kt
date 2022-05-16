package htw.gma_sose22.metronomprokit.metronome

import android.util.Log
import java.util.*
import kotlin.math.roundToInt

class Metronome(
    override val bpm: Int,
    override var sound: ByteArray,
    override val metronomeAudio: MetronomeAudioInterface
) : MetronomeInterface {

    companion object {
        const val DEFAULT_SPEED = 100
    }

    private var isPlaying = false
    private var playbackRunnable: Runnable? = null
    private var playbackThread: Thread? = null
    private var lastTimestamp: Long? = null

    override fun getIsPlaying(): Boolean {
        return isPlaying
    }

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

                Log.d("Metronome", "Space size " + space.size)

                val millisecondsSinceEpoch = System.currentTimeMillis()
                lastTimestamp?.let { lastTimestamp ->
                    val difference = millisecondsSinceEpoch - lastTimestamp
                    Log.d("Metronome", "Time diff: $difference")
                }
                lastTimestamp = millisecondsSinceEpoch
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