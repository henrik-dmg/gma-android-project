package htw.gma_sose22.metronomekit.beat

object BeatManager {

    private var beatPattern: BeatPattern? = null

    private var currentBeatIndex = 0u
    private var currentToneIndex = 0u
    private var currentRepetitionCount = 0u

    @Throws(BeatManagerException::class)
    fun loadBeat(beat: Beat) {
        val beats = arrayOf(beat)
        val beatPattern = BeatPattern(beats = beats)
        loadBeatPattern(beatPattern)
    }

    @Throws(BeatManagerException::class)
    fun loadBeatPattern(beatPattern: BeatPattern) {
        if (!beatPattern.isValid()) {
            throw BeatManagerException("The BeatPattern is invalid")
        }
        this.beatPattern = beatPattern
        reset()
    }

    fun reset() {
        this.currentBeatIndex = 0u
        this.currentToneIndex = 0u
        this.currentRepetitionCount = 0u
    }

    fun nextTone(): Tone? {
        return beatPattern?.let { nextToneInPattern(it) }
    }

    private fun nextToneInPattern(beatPattern: BeatPattern): Tone? {
        if (currentBeatIndex.toInt() >= beatPattern.beats.size) {
            return null
        }
        val currentBeat = beatPattern.beats[currentBeatIndex.toInt()]
        if (currentToneIndex == currentBeat.noteCount) {
            currentRepetitionCount++
            currentToneIndex = 0u
        }
        currentBeat.repetitions?.let { repetitions ->
            if (repetitions == currentRepetitionCount) {
                currentBeatIndex++
                currentRepetitionCount = 0u
                return nextToneInPattern(beatPattern)
            }
        }
        val tone = currentBeat.makeNotes()[currentToneIndex.toInt()]
        currentToneIndex++
        return tone
    }

}