package htw.gma_sose22.metronomekit.beat

class BeatManager: NextToneProvider, BeatPatternHandler {

    private var beatPattern: BeatPattern? = null

    private var currentBeatIndex = 0
    private var currentToneIndex = 0
    private var currentRepetitionCount = 0

    @Throws(BeatManagerException::class)
    override fun loadBeat(beat: Beat) {
        val beats = arrayOf(beat)
        val beatPattern = BeatPattern(beats = beats)
        loadBeatPattern(beatPattern)
    }

    @Throws(BeatManagerException::class)
    override fun loadBeatPattern(beatPattern: BeatPattern) {
        if (!beatPattern.isValid()) {
            throw BeatManagerException("The BeatPattern is invalid")
        }
        this.beatPattern = beatPattern
        reset()
    }

    fun reset() {
        this.currentBeatIndex = 0
        this.currentToneIndex = 0
        this.currentRepetitionCount = 0
    }

    override fun nextTone(): ToneMetadata? {
        return beatPattern?.let { nextToneInPattern(it) }
    }

    private fun nextToneInPattern(beatPattern: BeatPattern): ToneMetadata? {
        if (currentBeatIndex >= beatPattern.beats.size) {
            return null
        }
        val currentBeat = beatPattern.beats[currentBeatIndex]
        if (currentToneIndex == currentBeat.noteCount.toInt()) {
            currentRepetitionCount++
            currentToneIndex = 0
        }
        currentBeat.repetitions?.let { repetitions ->
            if (repetitions.toInt() == currentRepetitionCount) {
                currentBeatIndex++
                currentRepetitionCount = 0
                return nextToneInPattern(beatPattern)
            }
        }
        val tone = currentBeat.makeNotes()[currentToneIndex]
        val metadata = ToneMetadata(tone, currentToneIndex, currentBeatIndex)
        currentToneIndex++
        return metadata
    }

}