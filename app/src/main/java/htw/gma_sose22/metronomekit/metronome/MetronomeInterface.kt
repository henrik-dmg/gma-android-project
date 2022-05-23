package htw.gma_sose22.metronomekit.metronome

import htw.gma_sose22.metronomekit.audio.AudioControllable

interface MetronomeInterface: AudioControllable {
    var bpm: Int
    var sound: ByteArray
    val metronomeAudio: MetronomeAudioInterface
}