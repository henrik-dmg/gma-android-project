package htw.gma_sose22.metronomprokit.audio

interface AudioControllable {
    fun getIsPlaying() : Boolean

    fun play()
    fun stop()
    fun togglePlayback()
}