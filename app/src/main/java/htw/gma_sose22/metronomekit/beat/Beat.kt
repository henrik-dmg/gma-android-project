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