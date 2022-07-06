package htw.gma_sose22.metronomeui.library

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.databinding.LibraryListitemBinding
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomeui.util.BeatPatternDiffCallback
import htw.gma_sose22.metronomeui.util.ListAdapterItemClickListener

class LibraryAdapter(private val context: Context, private val clickListener: ListAdapterItemClickListener<BeatPattern>? = null) :
    ListAdapter<BeatPattern, LibraryViewHolder>(BeatPatternDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = LibraryListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LibraryViewHolder(binding, context).apply {
            onViewHolderCreated(this, viewType, binding)
        }
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val beatPattern = getItem(position)
        Log.d("LibraryAdapter", beatPattern.toString())
        holder.bind(beatPattern)
    }

    /**
     * Called when a ViewHolder is created. ViewHolder is either created first time or
     * when data is refreshed.
     *
     * This method is not called when RecyclerView is being scrolled
     */
    private fun onViewHolderCreated(
        viewHolder: RecyclerView.ViewHolder,
        viewType: Int,
        binding: LibraryListitemBinding
    ) {
        binding.root.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            clickListener?.handleClick(getItem(adapterPosition), adapterPosition)
        }
    }

}