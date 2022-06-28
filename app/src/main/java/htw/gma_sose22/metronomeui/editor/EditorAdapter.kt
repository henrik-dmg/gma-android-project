package htw.gma_sose22.metronomeui.editor

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.EditorlistItemBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.Tone

class EditorAdapter(val context: Context): ListAdapter<Beat, EditorAdapter.ViewHolder>(BeatDiffCallback) {

   inner class ViewHolder(private val binding: EditorlistItemBinding) : RecyclerView.ViewHolder(binding.root) {
      private val incrementNotesButton: Button = binding.beatView.tonesView.incrementNotesButton
      private val decrementNotesButton: Button = binding.beatView.tonesView.decrementNotesButton
      private var currentBeat: Beat? = null

      init {
      }

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

         for (i in 0 until binding.beatView.tonesView.beatButtons.childCount) {
            val button = binding.beatView.tonesView.beatButtons.getChildAt(i) as MaterialButton
            button.setOnClickListener {
               currentBeat?.let {
                  it.rotateNote(i)
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

         incrementNotesButton.isEnabled = beat.canAddNote
         decrementNotesButton.isEnabled = beat.canRemoveNote

         binding.beatView.tonesView.noteCountLabel.text = context.resources.getQuantityString(
            R.plurals.notes_count,
            beat.noteCount,
            beat.noteCount
         )
      }

      private fun updateButtonImage(button: MaterialButton, tone: Tone) {
         when (tone) {
            Tone.emphasised -> button.icon = context.resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = context.resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon = context.resources.getDrawable(R.drawable.ic_note_default)
         }
      }
   }

   /* Creates and inflates view and return FlowerViewHolder. */
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = EditorlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   /* Gets current flower and uses it to bind view. */
   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val beat = getItem(position)
      holder.bind(beat)
   }

   override fun onViewRecycled(holder: ViewHolder) {
      super.onViewRecycled(holder)
      holder.unbind()
   }

}