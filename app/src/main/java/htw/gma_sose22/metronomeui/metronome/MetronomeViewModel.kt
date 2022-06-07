package htw.gma_sose22.metronomeui.metronome

import android.util.Log
import androidx.lifecycle.*
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.BeatManager
import htw.gma_sose22.metronomekit.metronome.MetronomeService

class MetronomeViewModel : ViewModel() {

    private val mutableBeat = MutableLiveData<Beat>()
    private val mutableBPM = MutableLiveData<Int>()
    private val mutableIsPlaying = MutableLiveData<Boolean>()

    val beat: LiveData<Beat> = mutableBeat
    val bpm: LiveData<Int> = mutableBPM
    val isPlaying: LiveData<Boolean> = mutableIsPlaying

    init {
        mutableBeat.value = Beat(MetronomeService.bpm, 4,null, intArrayOf(0), null)
        mutableBPM.value = MetronomeService.bpm
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

    fun handleBPMChangeRequested(bpmDelta: Int) {
        MetronomeService.bpm += bpmDelta
        mutableBeat.value?.tempo = MetronomeService.bpm
        mutableBPM.value = MetronomeService.bpm
    }

    fun handleStartStopButtonClicked() {
        if (MetronomeService.isPlaying) {
            MetronomeService.stop()
        } else {
            mutableBeat.value.let {
                if (it is Beat) {
                    BeatManager.loadBeat(it)
                    MetronomeService.play()
                } else {
                    Log.e("MetronomeViewModel", "Beat does not exist, but it should")
                }
            }
        }
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

}