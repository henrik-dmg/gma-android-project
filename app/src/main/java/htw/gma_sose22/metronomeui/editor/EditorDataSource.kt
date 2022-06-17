package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import htw.gma_sose22.metronomekit.beat.Beat

object EditorDataSource {

    private val initialFlowerList = initialBeats()
    private val beatsLiveData = MutableLiveData(initialFlowerList)

    /* Adds beat to liveData and posts value. */
    fun addBeat(beat: Beat) {
        val currentList = beatsLiveData.value
        if (currentList == null) {
            beatsLiveData.postValue(listOf(beat))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(beat)
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

    /* Returns flower given an ID. */
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
        val basicBeat = Beat(120, 4, 10, null, null)
        return listOf(basicBeat)
    }

}