package htw.gma_sose22.metronomeui.library

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.R
import htw.gma_sose22.databinding.LibraryListitemBinding
import htw.gma_sose22.metronomekit.beat.BeatPattern

class LibraryViewHolder(private val binding: LibraryListitemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(beatPattern: BeatPattern) {
        binding.titleTextView.text = beatPattern.patternName
        beatPattern.numberOfBars.let { numberOfBars ->
            if (numberOfBars != null) {
                binding.subtitleTextView.text = context.resources.getQuantityString(
                    R.plurals.bars_count,
                    numberOfBars.toInt(),
                    numberOfBars.toInt()
                )
            } else {
                binding.subtitleTextView.text =
                    context.resources.getString(R.string.infinite_beat_subtitle)
            }
        }
    }
}