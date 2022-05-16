package htw.gma_sose22.metronomprokit.metronome

import htw.gma_sose22.metronomprokit.audio.AudioControllable
import htw.gma_sose22.metronomprokit.audio.AudioWriteable

interface MetronomeInterface: AudioControllable {
    var bpm: Int
    var sound: ByteArray
    val metronomeAudio: MetronomeAudioInterface
}