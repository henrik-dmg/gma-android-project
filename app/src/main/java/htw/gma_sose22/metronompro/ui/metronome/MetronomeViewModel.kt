package htw.gma_sose22.metronompro.ui.metronome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import htw.gma_sose22.metronomprokit.metronome.Metronome
import htw.gma_sose22.metronomprokit.metronome.MetronomeService

class MetronomeViewModel : ViewModel() {

    private val mutableBPM: MutableLiveData<Int> = MutableLiveData()

    val bpm: LiveData<Int>
        get() = mutableBPM

    init {
        mutableBPM.value = Metronome.DEFAULT_SPEED
    }

    fun handleBPMChangeRequested(bpmDelta: Int) {
        MetronomeService.changeBPM(bpmDelta)
        mutableBPM.value = MetronomeService.bpm
    }

    fun handleStartStopButtonClicked() {
        MetronomeService.togglePlayback()
    }
}