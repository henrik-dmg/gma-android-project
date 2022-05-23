package htw.gma_sose22.metronomprokit.metronome

import htw.gma_sose22.metronomprokit.audio.AudioControllable

interface MetronomeInterface: AudioControllable {
    var bpm: Int
    var sound: ByteArray
    val metronomeAudio: MetronomeAudioInterface
}