package htw.gma_sose22.metronomeui.editor

import androidx.recyclerview.widget.DiffUtil
import htw.gma_sose22.metronomekit.beat.Beat

object BeatDiffCallback: DiffUtil.ItemCallback<Beat>() {

    override fun areItemsTheSame(oldItem: Beat, newItem: Beat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Beat, newItem: Beat): Boolean {
        return oldItem.id == newItem.id
    }

}