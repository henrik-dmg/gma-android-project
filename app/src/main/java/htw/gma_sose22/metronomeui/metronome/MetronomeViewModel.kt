package htw.gma_sose22.metronomeui.metronome

import android.util.Log
import androidx.lifecycle.*
import htw.gma_sose22.metronomekit.beat.*
import htw.gma_sose22.metronomekit.metronome.Metronome
import htw.gma_sose22.metronomekit.metronome.MetronomeService

class MetronomeViewModel : ViewModel() {

    private val mutableBeat = MutableLiveData<Beat>()
    private val mutableBPM = MutableLiveData<Int>()
    private val mutableIsPlaying = MutableLiveData<Boolean>()

    val beat: LiveData<Beat> = mutableBeat
    val bpm: LiveData<Int> = mutableBPM
    val isPlaying: LiveData<Boolean> = mutableIsPlaying

    val mappedBPM: Int
        get() {
            val bpmDeltaFromMinimum = bpm.value!! - Metronome.MINIMUM_SPEED
            val bpmRange = Metronome.MAXIMUM_SPEED - Metronome.MINIMUM_SPEED
            return ((bpmDeltaFromMinimum.toDouble() / bpmRange.toDouble()) * 100).toInt()
        }

    init {
        mutableBeat.value = Beat(MetronomeService.bpm, 4,null, setOf(0), setOf())
        mutableBPM.value = MetronomeService.bpm
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

    fun addNoteToBeat() {
        mutableBeat.value?.let {
            if (it.noteCount < 8) {
                it.noteCount += 1
                Log.d("MetronomeViewModel", "Added note to beat")
                mutableBeat.postValue(it)
            }
        }
    }

    fun removeNoteFromBeat() {
        mutableBeat.value?.let {
            if (it.noteCount > 1) {
                it.noteCount -= 1
                Log.d("MetronomeViewModel", "Removed note from beat")
                mutableBeat.postValue(it)
            }
        }
    }

    fun rotateNoteTypeAtIndex(index: Int) {
        mutableBeat.value?.let {
            val notes = it.makeNotes()
            val currentNote = notes[index]
            Log.d("MetronomeViewModel", "Rotating note $currentNote")

            when (currentNote) {
                Tone.emphasised -> {
                    val mutableList = it.emphasisedNotes.toMutableSet()
                    mutableList.remove(index)
                    it.emphasisedNotes = mutableList
                    Log.d("MetronomeViewModel", "Next note Regular")
                }
                Tone.regular -> {
                    Log.d("MetronomeViewModel", "Next note muted")
                    val mutableMutedSet = it.mutedNotes.toMutableSet()
                    mutableMutedSet.add(index)
                    it.mutedNotes = mutableMutedSet
                }
                Tone.muted -> {
                    val mutableList = it.mutedNotes.toMutableSet()
                    mutableList.remove(index)
                    it.mutedNotes = mutableList

                    val mutableMutedSet = it.emphasisedNotes.toMutableSet()
                    mutableMutedSet.add(index)
                    it.emphasisedNotes = mutableMutedSet
                    Log.d("MetronomeViewModel", "Next note emphasised")
                }
            }

            Log.d("MetronomeViewModel", "New notes ${it.makeNotes()}")
            mutableBeat.postValue(it)
        }
    }

    fun setBPMMappedToAllowedRange(percentage: Double) {
        val minMaxDelta = Metronome.MAXIMUM_SPEED - Metronome.MINIMUM_SPEED
        val percentageBPM = (minMaxDelta.toDouble() * percentage).toInt()
        val targetBPM = Metronome.MINIMUM_SPEED + percentageBPM

        mutableBPM.value = targetBPM
        MetronomeService.bpm = targetBPM
        // Note that we're note updating the tempo of the beat here,
        // since otherwise the seekbar would be updated as well
    }

    fun handleStartStopButtonClicked() {
        if (MetronomeService.isPlaying) {
            MetronomeService.stop()
        } else {
            mutableBeat.value?.let {
                BeatManager.loadBeat(it)
                MetronomeService.play()
            }
        }
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

}