package htw.gma_sose22.metronomeui.metronome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.metronome.MetronomeService
import htw.gma_sose22.metronomeui.views.BPMModifiable
import htw.gma_sose22.metronomeui.views.ToneModifiable

class MetronomeViewModel : ViewModel(), BPMModifiable, ToneModifiable {

    private val mutableBeat = MutableLiveData<Beat>()
    private val mutableIsPlaying = MutableLiveData<Boolean>()

    val beat: LiveData<Beat> = mutableBeat
    val isPlaying: LiveData<Boolean> = mutableIsPlaying

    init {
        mutableBeat.value = Beat(MetronomeService.bpm, 4u, null, setOf(0u), setOf())
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

    fun handleStartStopButtonClicked() {
        if (MetronomeService.isPlaying) {
            MetronomeService.stop()
        } else {
            mutableBeat.value?.let {
                MetronomeService.loadBeat(it)
                MetronomeService.play()
            }
        }
        mutableIsPlaying.value = MetronomeService.isPlaying
    }

    fun playbackStopped() {
        handleStartStopButtonClicked()
    }

    override fun modifyBPM(bpmDelta: Int) {
        mutableBeat.value?.let {
            it.modifyBPM(bpmDelta)
            MetronomeService.bpm = it.tempo
            mutableBeat.postValue(it)
        }
    }

    override fun addNote() {
        mutableBeat.value?.let {
            if (it.addNote()) {
                mutableBeat.postValue(it)
            }
        }
    }

    override fun removeNote() {
        mutableBeat.value?.let {
            if (it.removeNote()) {
                mutableBeat.postValue(it)
            }
        }
    }

    override fun rotateNote(index: Int) {
        mutableBeat.value?.let {
            it.rotateNote(index.toUInt())
            mutableBeat.postValue(it)
        }
    }

}