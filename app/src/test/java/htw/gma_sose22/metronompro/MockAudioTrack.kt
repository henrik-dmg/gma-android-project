package htw.gma_sose22.metronompro

import htw.gma_sose22.metronomprokit.metronome.MetronomeAudioInterface

class MockAudioTrack(override val sampleRate: Int) : MetronomeAudioInterface {

    var audioWriteCalls = 0
    var playCalls = 0
    var stopCalls = 0

    override fun write(audioData: ByteArray, offsetInBytes: Int, sizeInBytes: Int): Int {
        audioWriteCalls++
        return audioData.size
    }

    override fun play() {
        playCalls++
    }

    override fun stop() {
        stopCalls++
    }
}