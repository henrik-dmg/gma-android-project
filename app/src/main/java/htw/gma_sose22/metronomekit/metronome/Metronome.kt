package htw.gma_sose22.metronomekit.metronome

class Metronome(
    override var bpm: Int,
    override var sound: ByteArray,
    override val metronomeAudio: MetronomeAudioInterface
) : MetronomeInterface {

    companion object {
        const val DEFAULT_SAMPLE_RATE = 44100
        const val DEFAULT_SPEED = 120
    }

    override var isPlaying = false
        private set

    private var playbackRunnable: Runnable? = null
    private var playbackThread: Thread? = null

    override fun play() {
        metronomeAudio.play()
        isPlaying = true
        playbackRunnable = configureRunnable()
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

    private fun configureRunnable(): Runnable {
        return Runnable {
            // this active waiting is okay here, because metronomeAudio blocks until
            // the audio buffer has been emptied. This way we get precise gaps between taps
            while (isPlaying) {
                // a sample consists of two bytes instead of one
                // https://github.com/pandapaul/BadMetronome/commit/d84083a455b974fc37625da5789f9dec881e1094
                val sampleRateForGapCalculation = metronomeAudio.sampleRate * 2

                val bufferInfo = AudioGapCalculator.calculateBeatLength(
                    bpm,
                    sampleRateForGapCalculation,
                    sound.size
                )

                metronomeAudio.write(sound, 0, bufferInfo.soundLength)
                val spaceBytes = ByteArray(bufferInfo.spaceLength)
                metronomeAudio.write(spaceBytes, 0, spaceBytes.size)
            }
        }
    }

}