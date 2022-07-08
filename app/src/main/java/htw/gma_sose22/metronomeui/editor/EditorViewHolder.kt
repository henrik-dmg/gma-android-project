package htw.gma_sose22.metronomeui.editor

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.databinding.EditorListitemBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomeui.views.BPMModifiable
import htw.gma_sose22.metronomeui.views.RepetitionModifiable
import htw.gma_sose22.metronomeui.views.ToneModifiable

class EditorViewHolder(private val binding: EditorListitemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root), BPMModifiable, ToneModifiable, RepetitionModifiable {

    private var currentBeat: Beat? = null

    init {
        binding.bpmModificationView.bind(this)
        binding.tonesView.bind(this)
        binding.repetitionsView.bind(this)
    }

    fun bind(beat: Beat) {
        currentBeat = beat
        updateBeatView(beat)
    }

    fun unbind() {
        binding.bpmModificationView.unbind()
        binding.tonesView.unbind()
        binding.repetitionsView.unbind()
        currentBeat = null
    }

    private fun updateBeatView(beat: Beat) {
        Log.d("EditorAdapter", "Updated view with beat $beat")

        binding.bpmModificationView.updateView(beat)
        binding.tonesView.updateView(beat)
        binding.repetitionsView.updateView(beat)
    }

    fun highlightNote(toneIndex: Int) {
        TODO("Actually highlight note")
    }

    override fun modifyBPM(bpmDelta: Int) {
        currentBeat?.let {
            it.modifyBPM(bpmDelta)
            updateBeatView(it)
        }
    }

    override fun addNote() {
        currentBeat?.let {
            if (it.addNote()) {
                updateBeatView(it)
            }
        }
    }

    override fun removeNote() {
        currentBeat?.let {
            if (it.removeNote()) {
                updateBeatView(it)
            }
        }
    }

    override fun rotateNote(index: Int) {
        currentBeat?.let {
            it.rotateNote(index.toUInt())
            updateBeatView(it)
        }
    }

    override fun addRepetition() {
        currentBeat?.let {
            if (it.addRepetition()) {
                updateBeatView(it)
            }
        }
    }

    override fun removeRepetition() {
        currentBeat?.let {
            if (it.removeRepetition()) {
                updateBeatView(it)
            }
        }
    }

}