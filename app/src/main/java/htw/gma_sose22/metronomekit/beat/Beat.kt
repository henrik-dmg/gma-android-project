package htw.gma_sose22.metronomekit.beat

import android.util.Log
import htw.gma_sose22.metronomekit.util.Validateable
import java.util.*

data class Beat(
    var tempo: Int = 120,
    var noteCount: Int = 4,
    var repetitions: Int? = null,
    var emphasisedNotes: Set<Int> = setOf(),
    var mutedNotes: Set<Int> = setOf()
): Validateable {

    val id = UUID.randomUUID().toString()

    val canAddNote: Boolean
        get() { return noteCount < 8 }
    val canRemoveNote: Boolean
        get() { return noteCount > 1 }

    fun addNote(): Boolean {
        if (noteCount < 8) {
            noteCount += 1
            cleanUpSets()
            Log.d("Beat", "Added note to beat")
            return true
        }
        return false
    }

    fun removeNote(): Boolean {
        if (noteCount > 1) {
            noteCount -= 1
            cleanUpSets()
            Log.d("Beat", "Removed note from beat")
            return true
        }
        return false
    }

    private fun cleanUpSets() {
        val mutableMutedSet = mutedNotes.toMutableSet()
        mutableMutedSet.removeAll { index -> index >= noteCount }
        this.mutedNotes = mutableMutedSet

        val mutableEmphasisedSet = emphasisedNotes.toMutableSet()
        mutableEmphasisedSet.removeAll { index -> index >= noteCount }
        this.emphasisedNotes = mutableEmphasisedSet
    }

    override fun isValid(): Boolean {
        return emphasisedNotes.none { index -> index >= noteCount }
                && mutedNotes.none { index -> index >= noteCount }
                && (mutedNotes.intersect(emphasisedNotes).isEmpty()) // check that muted and emphasised notes are disjoint
    }

    fun rotateNote(index: Int) {
        // Flow is emphasised -> normal -> muted
        if (emphasisedNotes.contains(index)) {
            normalizeNote(index)
        } else if (mutedNotes.contains(index)) {
            emphasiseNote(index)
        } else {
            muteNote(index)
        }
    }

    private fun normalizeNote(index: Int) {
        if (index >= noteCount) {
            throw IndexOutOfBoundsException()
        }

        val mutableMutedSet = mutedNotes.toMutableSet()
        mutableMutedSet.remove(index)
        this.mutedNotes = mutableMutedSet

        val mutableEmphasisedSet = emphasisedNotes.toMutableSet()
        mutableEmphasisedSet.remove(index)
        this.emphasisedNotes = mutableEmphasisedSet
    }

    private fun emphasiseNote(index: Int) {
        if (index >= noteCount) {
            throw IndexOutOfBoundsException()
        }

        val mutableMutedSet = mutedNotes.toMutableSet()
        mutableMutedSet.remove(index)
        this.mutedNotes = mutableMutedSet

        val mutableEmphasisedSet = emphasisedNotes.toMutableSet()
        mutableEmphasisedSet.add(index)
        this.emphasisedNotes = mutableEmphasisedSet
    }

    private fun muteNote(index: Int) {
        if (index >= noteCount) {
            throw IndexOutOfBoundsException()
        }

        val mutableMutedSet = mutedNotes.toMutableSet()
        mutableMutedSet.add(index)
        this.mutedNotes = mutableMutedSet

        val mutableEmphasisedSet = emphasisedNotes.toMutableSet()
        mutableEmphasisedSet.remove(index)
        this.emphasisedNotes = mutableEmphasisedSet
    }

    fun makeNotes(): Array<Tone> {
        val notes = Array(noteCount) { Tone.regular }
        emphasisedNotes.forEach { index ->
            notes[index] = Tone.emphasised
        }
        mutedNotes.forEach { index ->
            notes[index] = Tone.muted
        }
        return notes
    }

}