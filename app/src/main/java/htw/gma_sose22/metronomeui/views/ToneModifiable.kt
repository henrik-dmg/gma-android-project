package htw.gma_sose22.metronomeui.views

interface ToneModifiable {
    fun addNote()
    fun removeNote()
    fun rotateNote(index: Int)
}