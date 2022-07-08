package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomekit.metronome.MetronomeService

class EditorViewModel(val beatPattern: BeatPattern?) : ViewModel() {

    private val beatsLiveData: MutableLiveData<List<Beat>>
    val beats: LiveData<List<Beat>>

    private val isPlayingLiveData = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = isPlayingLiveData

    init {
        beatsLiveData = if (beatPattern != null) {
            MutableLiveData(beatPattern.beats.toList())
        } else {
            MutableLiveData(initialBeats())
        }
        beats = beatsLiveData
    }

    fun addBeat() {
        val newBeat = Beat(120, 4u, 4u, setOf(0u), setOf())
        addBeat(newBeat)
    }

    /* Adds beat to liveData and posts value. */
    private fun addBeat(beat: Beat) {
        val currentList = beatsLiveData.value
        if (currentList == null) {
            beatsLiveData.postValue(listOf(beat))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(updatedList.size, beat)
            beatsLiveData.postValue(updatedList)
        }
    }

    fun removeAllBeats() {
        beatsLiveData.postValue(initialBeats())
    }

    /* Removes beat at index from liveData and posts value. */
    fun removeBeat(index: Int) {
        beatsLiveData.value?.let { currentList ->
            val updatedList = currentList.toMutableList()
            updatedList.removeAt(index)
            beatsLiveData.postValue(updatedList)
        }
    }

    /* Removes beat at index from liveData and posts value. */
    fun restoreBeat(beat: Beat?, index: Int) {
        beat?.let {
            val currentList = beatsLiveData.value
            if (currentList == null) {
                beatsLiveData.postValue(listOf(beat))
            } else {
                val updatedList = currentList.toMutableList()
                updatedList.add(index, beat)
                beatsLiveData.postValue(updatedList)
            }
        }
    }

    fun makePattern(patternName: String): BeatPattern? {
        beatsLiveData.value?.let { beats ->
            return if (beatPattern != null) {
                beatPattern.beats = beats.toTypedArray()
                return beatPattern
            } else {
                BeatPattern(patternName = patternName, beats = beats.toTypedArray())
            }
        }
        return null
    }

    private fun initialBeats(): List<Beat> {
        val basicBeat = Beat(120, 4u, 10u, setOf(0u), setOf())
        return listOf(basicBeat)
    }

    fun handlePlaybackButtonTapped() {
        if (MetronomeService.isPlaying) {
            MetronomeService.stop()
        } else {
            makePattern("Temp Pattern")?.let {
                MetronomeService.loadBeatPattern(it)
                MetronomeService.play()
            }
        }
        isPlayingLiveData.postValue(MetronomeService.isPlaying)
    }

    fun playbackStopped() {
        handlePlaybackButtonTapped()
    }

}