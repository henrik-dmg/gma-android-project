package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.*
import htw.gma_sose22.metronomekit.beat.Beat

class EditorViewModel: ViewModel() {

    private val beatsLiveData = MutableLiveData(initialBeats())

    val beats: LiveData<List<Beat>> = beatsLiveData

    fun addBeat() {
        val newBeat = Beat(120, 4, 4, setOf(0), setOf())
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

    /* Removes beat from liveData and posts value. */
    fun removeBeat(beat: Beat) {
        val currentList = beatsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(beat)
            beatsLiveData.postValue(updatedList)
        }
    }

    /* Removes beat at index from liveData and posts value. */
    fun removeBeat(index: Int) {
        val currentList = beatsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.removeAt(index)
            beatsLiveData.postValue(updatedList)
        }
    }

    fun getBeatForID(id: String): Beat? {
        beatsLiveData.value?.let { beats ->
            return beats.firstOrNull{ it.id == id }
        }
        return null
    }

    fun getBeatList(): LiveData<List<Beat>> {
        return beatsLiveData
    }

    private fun initialBeats(): List<Beat> {
        val basicBeat = Beat(120, 4, 10, setOf(0), setOf())
        return listOf(basicBeat)
    }

}