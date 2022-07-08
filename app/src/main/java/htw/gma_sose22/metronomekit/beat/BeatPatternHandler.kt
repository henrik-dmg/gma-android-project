package htw.gma_sose22.metronomekit.beat

interface BeatPatternHandler {
    @Throws(BeatManagerException::class)
    fun loadBeat(beat: Beat)

    @Throws(BeatManagerException::class)
    fun loadBeatPattern(beatPattern: BeatPattern)
}