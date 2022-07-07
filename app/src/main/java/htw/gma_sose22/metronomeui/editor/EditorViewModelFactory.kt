package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import htw.gma_sose22.metronomekit.beat.BeatPattern

class EditorViewModelFactory(val beatPattern: BeatPattern?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = EditorViewModel(beatPattern) as T
}