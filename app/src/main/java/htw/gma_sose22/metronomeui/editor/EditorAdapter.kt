package htw.gma_sose22.metronomeui.editor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import htw.gma_sose22.databinding.EditorListitemBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomeui.util.BeatDiffCallback

class EditorAdapter(val context: Context) :
    ListAdapter<Beat, EditorViewHolder>(BeatDiffCallback()) {

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditorViewHolder {
        val binding = EditorListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EditorViewHolder(binding, context)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: EditorViewHolder, position: Int) {
        val beat = getItem(position)
        holder.bind(beat)
    }

    override fun onViewRecycled(holder: EditorViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

}