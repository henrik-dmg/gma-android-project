package htw.gma_sose22.metronomekit.beat

import android.util.Log
import htw.gma_sose22.metronomekit.util.Validateable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Beat(
    var tempo: Int = 120,
    var noteCount: UInt = 4u,
    var repetitions: UInt? = null,
    var emphasisedNotes: Set<UInt> = setOf(),
    var mutedNotes: Set<UInt> = setOf()
) : Validateable {

    companion object {
        const val MINIMUM_NOTES = 1u
        const val MAXIMUM_NOTES = 8u
        const val MINIMUM_SPEED = 40
        const val MAXIMUM_SPEED = 200
    }

    val id = UUID.randomUUID().toString()

    val canAddNote: Boolean
        get() {
            return noteCount < MAXIMUM_NOTES
        }
    val canRemoveNote: Boolean
        get() {
            return noteCount > MINIMUM_NOTES
        }
    val canDecreaseBPM: Boolean
        get() {
            return tempo > MINIMUM_SPEED
        }
    val canIncreaseBPM: Boolean
        get() {
            return tempo < MAXIMUM_SPEED
        }

    fun modifyBPM(bpmDelta: Int) {
        var newTempo = tempo + bpmDelta
        if (newTempo < MINIMUM_SPEED) {
            newTempo = MINIMUM_SPEED
        } else if (newTempo > MAXIMUM_SPEED) {
            newTempo = MAXIMUM_SPEED
        }
        this.tempo = newTempo
    }

    fun addNote(): Boolean {
        if (canAddNote) {
            noteCount += 1u
            cleanUpSets()
            Log.d("Beat", "Added note to beat")
            return true
        }
        return false
    }

    fun removeNote(): Boolean {
        if (canRemoveNote) {
            noteCount -= 1u
            cleanUpSets()
            Log.d("Beat", "Removed note from beat")
            return true
        }
        return false
    }

    override fun isValid(): Boolean {
        return emphasisedNotes.none { index -> index >= noteCount }
                && mutedNotes.none { index -> index >= noteCount }
                && (mutedNotes.intersect(emphasisedNotes)
            .isEmpty()) // check that muted and emphasised notes are disjoint
                && tempo >= MINIMUM_SPEED
                && tempo <= MAXIMUM_SPEED
                && noteCount > 0u
                && noteCount <= MAXIMUM_NOTES
    }

    fun makeNotes(): Array<Tone> {
        val notes = Array(noteCount.toInt()) { Tone.regular }
        emphasisedNotes.forEach { index ->
            notes[index.toInt()] = Tone.emphasised
        }
        mutedNotes.forEach { index ->
            notes[index.toInt()] = Tone.muted
        }
        return notes
    }

    fun rotateNote(index: UInt) {
        // Flow is emphasised -> normal -> muted
        if (emphasisedNotes.contains(index)) {
            normalizeNote(index)
        } else if (mutedNotes.contains(index)) {
            emphasiseNote(index)
        } else {
            muteNote(index)
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun normalizeNote(index: UInt) {
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

    private fun emphasiseNote(index: UInt) {
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

    private fun muteNote(index: UInt) {
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

    private fun cleanUpSets() {
        val mutableMutedSet = mutedNotes.toMutableSet()
        mutableMutedSet.removeAll { index -> index >= noteCount }
        this.mutedNotes = mutableMutedSet

        val mutableEmphasisedSet = emphasisedNotes.toMutableSet()
        mutableEmphasisedSet.removeAll { index -> index >= noteCount }
        this.emphasisedNotes = mutableEmphasisedSet
    }

}