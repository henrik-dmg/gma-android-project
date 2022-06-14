package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import htw.gma_sose22.metronomekit.beat.Beat

object EditorDataSource {

    private val initialFlowerList = initialBeats()
    private val flowersLiveData = MutableLiveData(initialFlowerList)

    /* Adds flower to liveData and posts value. */
    fun addFlower(flower: Beat) {
        val currentList = flowersLiveData.value
        if (currentList == null) {
            flowersLiveData.postValue(listOf(flower))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, flower)
            flowersLiveData.postValue(updatedList)
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeFlower(flower: Beat) {
        val currentList = flowersLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(flower)
            flowersLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getFlowerForId(id: String): Beat? {
        flowersLiveData.value?.let { flowers ->
            return flowers.firstOrNull{ it.id == id }
        }
        return null
    }

    fun getBeatList(): LiveData<List<Beat>> {
        return flowersLiveData
    }

    private fun initialBeats(): List<Beat> {
        val basicBeat = Beat(120, 4, 10, null, null)
        return listOf(basicBeat)
    }

}