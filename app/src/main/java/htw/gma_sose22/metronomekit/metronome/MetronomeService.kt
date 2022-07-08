package htw.gma_sose22.metronomekit.metronome

import htw.gma_sose22.metronomekit.audio.AudioControllable
import htw.gma_sose22.metronomekit.beat.*

object MetronomeService : AudioControllable, NextToneProvider, BeatPatternHandler {

    private lateinit var metronome: MetronomeInterface
    private val beatManager = BeatManager()

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

    override fun loadBeat(beat: Beat) {
        beatManager.loadBeat(beat)
    }

    override fun loadBeatPattern(beatPattern: BeatPattern) {
        beatManager.loadBeatPattern(beatPattern)
    }

    override fun nextTone(): ToneMetadata? {
        val metadata = beatManager.nextTone()
        // TODO: Call change handler to notify of changes
        return metadata
    }

    fun configureMetronome(beatSound: ByteArray, offbeatSound: ByteArray, metronomeAudio: MetronomeAudioInterface) {
        metronome = Metronome(beatSound = beatSound, offbeatSound = offbeatSound, metronomeAudio = metronomeAudio, nextToneProvider = this)
    }

}