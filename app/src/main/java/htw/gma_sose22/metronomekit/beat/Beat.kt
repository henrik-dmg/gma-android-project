package htw.gma_sose22.metronomekit.beat

import htw.gma_sose22.metronomekit.util.Validateable
import java.util.*

data class Beat(
    var tempo: Int,
    var noteCount: Int,
    var repetitions: Int?,
    var emphasisedNotes: Set<Int>,
    var mutedNotes: Set<Int>
): Validateable {

    val id = UUID.randomUUID().toString()

    constructor(tempo: Int, noteCount: Int, repetitions: Int?) : this(tempo, noteCount, repetitions, setOf(), setOf())

    override fun isValid(): Boolean {
        emphasisedNotes.forEach { index ->
            if (index >= noteCount) {
                return false
            }
        }
        mutedNotes.forEach { index ->
            if (index >= noteCount) {
                return false
            }
        }
        return true
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

    fun normalizeNote(index: Int) {
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

    fun emphasiseNote(index: Int) {
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

    fun muteNote(index: Int) {
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