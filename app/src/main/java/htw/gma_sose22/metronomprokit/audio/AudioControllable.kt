package htw.gma_sose22.metronomprokit.audio

interface AudioControllable: StatelessAudioControllable {
    val isPlaying: Boolean

    fun togglePlayback()
}