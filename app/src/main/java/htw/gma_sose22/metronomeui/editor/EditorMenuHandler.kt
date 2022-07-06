package htw.gma_sose22.metronomeui.editor

import htw.gma_sose22.metronomekit.beat.BeatPattern

interface EditorMenuHandler {
    fun handleSaveButtonClicked()
    fun handleDeleteButtonClicked()

    fun overwriteViewModelWithPattern(pattern: BeatPattern)
}