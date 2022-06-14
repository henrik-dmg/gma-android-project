package htw.gma_sose22.metronomeui.editor

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomepro.R

class EditorAdapter(private val onClick: (Beat) -> Unit) : ListAdapter<Beat, EditorAdapter.EditorItemViewHolder>(BeatDiffCallback) {

   /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
   class EditorItemViewHolder(itemView: View, val onClick: (Beat) -> Unit) : RecyclerView.ViewHolder(itemView) {
      private val flowerTextView: TextView = itemView.findViewById(R.id.beat_word)
      private var currentFlower: Beat? = null

      init {
         itemView.setOnClickListener {
            currentFlower?.let {
               onClick(it)
            }
         }
      }

      /* Bind flower name and image. */
      fun bind(beat: Beat) {
         currentFlower = beat
         flowerTextView.text = beat.toString()
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