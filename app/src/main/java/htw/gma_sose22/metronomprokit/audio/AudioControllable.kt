package htw.gma_sose22.metronomprokit.audio

interface AudioControllable: StatelessAudioControllable {
    fun getIsPlaying() : Boolean

    fun togglePlayback()
}