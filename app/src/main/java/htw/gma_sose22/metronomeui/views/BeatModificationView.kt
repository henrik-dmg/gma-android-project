package htw.gma_sose22.metronomeui.views

import htw.gma_sose22.metronomekit.beat.Beat

interface BeatModificationView<T> {
    fun bind(modifiable: T)
    fun unbind()
    fun updateView(beat: Beat)
}