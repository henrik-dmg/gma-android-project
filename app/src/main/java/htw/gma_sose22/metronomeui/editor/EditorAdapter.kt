package htw.gma_sose22.metronomeui.editor

import android.view.*
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.R
import htw.gma_sose22.metronomekit.beat.Beat

class EditorAdapter: ListAdapter<Beat, EditorAdapter.ViewHolder>(BeatDiffCallback) {

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      private val incrementNotesButton: Button = itemView.findViewById(R.id.increment_notes_button)
      private val decrementNotesButton: Button = itemView.findViewById(R.id.decrement_notes_button)
      private var currentBeat: Beat? = null

      init {
      }

      fun bind(beat: Beat) {
         currentBeat = beat
      }

      fun unbind() {
         currentBeat = null
      }
   }

   /* Creates and inflates view and return FlowerViewHolder. */
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val context = parent.context
      val inflater = LayoutInflater.from(context)
      // Inflate the custom layout
      val editorListItem = inflater.inflate(R.layout.editorlist_item, parent, false)
      // Return a new holder instance
      return ViewHolder(editorListItem)
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