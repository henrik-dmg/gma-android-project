package htw.gma_sose22.metronomekit.beat

interface ToneChangeHandler {
    fun currentToneChanged(beatID: String, toneIndex: Int)
    fun playbackStopped()
}