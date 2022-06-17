package htw.gma_sose22.metronomeui.editor

import android.view.*
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomepro.R

class EditorAdapter(private val onClick: (Beat) -> Unit) : ListAdapter<Beat, EditorAdapter.EditorItemViewHolder>(BeatDiffCallback) {

   /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
   class EditorItemViewHolder(itemView: View, val onClick: (Beat) -> Unit) : RecyclerView.ViewHolder(itemView) {
      private val incrementNotesButton: Button = itemView.findViewById(R.id.increment_notes_button)
      private val decrementNotesButton: Button = itemView.findViewById(R.id.decrement_notes_button)
      private var currentBeat: Beat? = null

      init {
      }

      /* Bind flower name and image. */
      fun bind(beat: Beat) {
         currentBeat = beat
      }
   }

   /* Creates and inflates view and return FlowerViewHolder. */
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditorItemViewHolder {
      val view = LayoutInflater.from(parent.context)
         .inflate(R.layout.editorlist_item, parent, false)
      return EditorItemViewHolder(view, onClick)
   }

   /* Gets current flower and uses it to bind view. */
   override fun onBindViewHolder(holder: EditorItemViewHolder, position: Int) {
      val beat = getItem(position)
      holder.bind(beat)
   }

}