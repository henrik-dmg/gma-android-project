package htw.gma_sose22.metronomeui.util

import androidx.recyclerview.widget.DiffUtil
import htw.gma_sose22.metronomekit.beat.BeatPattern

class BeatPatternDiffCallback : DiffUtil.ItemCallback<BeatPattern>() {

    override fun areItemsTheSame(oldItem: BeatPattern, newItem: BeatPattern): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BeatPattern, newItem: BeatPattern): Boolean {
        return oldItem.id == newItem.id
                && oldItem.patternName == newItem.patternName
                && oldItem.createdAt == newItem.createdAt
                && oldItem.beats.contentEquals(newItem.beats)
    }
}