package htw.gma_sose22.metronomepro

import htw.gma_sose22.metronomekit.metronome.MetronomeAudioInterface

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