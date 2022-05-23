package htw.gma_sose22.metronomeui.metronome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import htw.gma_sose22.metronomekit.metronome.MetronomeService

class MetronomeViewModel : ViewModel() {

    private val mutableBPM = MutableLiveData<Int>()
    private val mutableIsPlaying = MutableLiveData<Boolean>()

    val bpm: LiveData<Int>
        get() = mutableBPM
    val isPlaying: LiveData<Boolean>
        get() = mutableIsPlaying

    init {
        mutableBPM.value = MetronomeService.bpm
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

    fun handleBPMChangeRequested(bpmDelta: Int) {
        MetronomeService.bpm += bpmDelta
        mutableBPM.value = MetronomeService.bpm
    }

    fun handleStartStopButtonClicked() {
        MetronomeService.togglePlayback()
        mutableIsPlaying.value = MetronomeService.isPlaying
    }
}