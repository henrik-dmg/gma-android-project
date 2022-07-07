package htw.gma_sose22.metronomeui.editor

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.EditorListitemBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.Tone

class EditorViewHolder(private val binding: EditorListitemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    private val incrementNotesButton: Button = binding.beatView.tonesView.incrementNotesButton
    private val decrementNotesButton: Button = binding.beatView.tonesView.decrementNotesButton
    private var currentBeat: Beat? = null

    fun bind(beat: Beat) {
        currentBeat = beat
        incrementNotesButton.setOnClickListener {
            currentBeat?.let {
                if (it.addNote()) {
                    updateBeatView(it)
                }
            }
        }
        decrementNotesButton.setOnClickListener {
            currentBeat?.let {
                if (it.removeNote()) {
                    updateBeatView(it)
                }
            }
        }

        binding.beatView.bpmModificationView.smallBpmDecrementButton.setOnClickListener {
            currentBeat?.let {
                it.modifyBPM(-1)
                updateBeatView(it)
            }
        }
        binding.beatView.bpmModificationView.largeBpmDecrementButton.setOnClickListener {
            currentBeat?.let {
                it.modifyBPM(-10)
                updateBeatView(it)
            }
        }
        binding.beatView.bpmModificationView.smallBpmIncrementButton.setOnClickListener {
            currentBeat?.let {
                it.modifyBPM(1)
                updateBeatView(it)
            }
        }
        binding.beatView.bpmModificationView.largeBpmIncrementButton.setOnClickListener {
            currentBeat?.let {
                it.modifyBPM(10)
                updateBeatView(it)
            }
        }

        for (i in 0 until binding.beatView.tonesView.beatButtons.childCount) {
            val button = binding.beatView.tonesView.beatButtons.getChildAt(i) as MaterialButton
            button.setOnClickListener {
                currentBeat?.let {
                    it.rotateNote(i.toUInt())
                    updateBeatView(it)
                }
            }
        }

        updateBeatView(beat)
    }

    fun unbind() {
        currentBeat = null
    }

    private fun updateBeatView(beat: Beat) {
        Log.d("EditorAdapter", "Updated view with beat $beat")
        val beatButtons = binding.beatView.tonesView.beatButtons
        val currentNumberOfButtons = beatButtons.childCount

        val tones = beat.makeNotes()

        for (i in 0 until currentNumberOfButtons) {
            val button = beatButtons.getChildAt(i)
            if (i < tones.size) {
                button.visibility = View.VISIBLE
                updateButtonImage(button as MaterialButton, tones[i])
            } else {
                button.visibility = View.GONE
            }
        }

        binding.beatView.bpmModificationView.bpmTextView.text = context.resources.getString(R.string.bpm_count, beat.tempo)
        incrementNotesButton.isEnabled = beat.canAddNote
        decrementNotesButton.isEnabled = beat.canRemoveNote
        binding.beatView.bpmModificationView.smallBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        binding.beatView.bpmModificationView.largeBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        binding.beatView.bpmModificationView.smallBpmDecrementButton.isEnabled = beat.canDecreaseBPM
        binding.beatView.bpmModificationView.largeBpmDecrementButton.isEnabled = beat.canDecreaseBPM
        binding.beatView.tonesView.noteCountLabel.text = context.resources.getQuantityString(
            R.plurals.notes_count,
            beat.noteCount.toInt(),
            beat.noteCount.toInt()
        )
    }

    private fun updateButtonImage(button: MaterialButton, tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = context.resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_default)
        }
    }
}