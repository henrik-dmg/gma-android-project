package htw.gma_sose22.metronomekit.audio

interface AudioControllable: StatelessAudioControllable {
    val isPlaying: Boolean

    fun togglePlayback()
}