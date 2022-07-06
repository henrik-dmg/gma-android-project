package htw.gma_sose22.metronomeui.util

interface ListAdapterItemClickListener<T> {
    fun handleClick(item: T, adapterPosition: Int)
}