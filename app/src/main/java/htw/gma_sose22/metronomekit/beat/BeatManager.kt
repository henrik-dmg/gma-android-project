package htw.gma_sose22.metronomekit.beat

class BeatManager {

    private var beatPattern: BeatPattern? = null

    private var currentBeatIndex = 0
    private var currentToneIndex = 0
    private var currentRepetitionCount = 0

    fun loadBeat(beatPattern: BeatPattern) {
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

    fun nextTone(): Tone? {
        return beatPattern?.let { nextToneInPattern(it) }
    }

    private fun nextToneInPattern(beatPattern: BeatPattern): Tone? {
        if (currentBeatIndex >= beatPattern.beats.size) {
            return null
        }
        val currentBeat = beatPattern.beats[currentBeatIndex]
        if (currentToneIndex == currentBeat.noteCount) {
            currentRepetitionCount++
            currentToneIndex = 0
        }
        currentBeat.repetitions?.let { repetitions ->
            if (repetitions == currentRepetitionCount) {
                currentBeatIndex++
                currentRepetitionCount = 0
                return nextToneInPattern(beatPattern)
            }
        }
        val tone = currentBeat.makeNotes()[currentToneIndex]
        currentToneIndex ++
        return tone
    }

//    private fun makeTonesForCurrentBeat(): Array<Tone>? {
//        val currentBeat = beatPattern?.beats?.get(currentBeatIndex)
//    }

}