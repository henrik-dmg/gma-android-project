package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.ViewModel
import htw.gma_sose22.metronomekit.beat.Beat

class EditorViewModel: ViewModel() {

    val beatsLiveData = EditorDataSource.getBeatList()

    fun addNewBeat() {
        EditorDataSource.addBeat(Beat(120, 4, 6, null, null))
    }

}