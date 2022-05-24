package htw.gma_sose22.metronomeui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home fragment"
    }
    val text: LiveData<String> = _text

}