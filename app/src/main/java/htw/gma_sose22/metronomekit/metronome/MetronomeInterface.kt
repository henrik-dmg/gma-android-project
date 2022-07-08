package htw.gma_sose22.metronomekit.metronome

import htw.gma_sose22.metronomekit.audio.AudioControllable
import htw.gma_sose22.metronomekit.beat.NextToneProvider

interface MetronomeInterface : AudioControllable {
    var bpm: Int
    var beatSound: ByteArray
    var offbeatSound: ByteArray
    val metronomeAudio: MetronomeAudioInterface
    val nextToneProvider: NextToneProvider
}