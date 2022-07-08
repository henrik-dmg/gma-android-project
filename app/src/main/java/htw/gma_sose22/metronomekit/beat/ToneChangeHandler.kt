package htw.gma_sose22.metronomekit.beat

interface ToneChangeHandler {
    fun currentToneChanged(toneIndex: Int, beatIndex: Int)
    fun playbackStopped()
}