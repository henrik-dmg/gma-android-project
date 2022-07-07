package htw.gma_sose22.metronomeui.util

import androidx.recyclerview.widget.DiffUtil
import htw.gma_sose22.metronomekit.beat.Beat

class BeatDiffCallback : DiffUtil.ItemCallback<Beat>() {

    override fun areItemsTheSame(oldItem: Beat, newItem: Beat): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Beat, newItem: Beat): Boolean {
        return oldItem.id == newItem.id
                && oldItem.noteCount == newItem.noteCount
                && oldItem.tempo == newItem.tempo
                && oldItem.repetitions == newItem.repetitions
                && oldItem.mutedNotes == newItem.mutedNotes
                && oldItem.emphasisedNotes == newItem.emphasisedNotes
    }

}