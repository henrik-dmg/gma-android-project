package htw.gma_sose22.metronomprokit

interface AudioGeneratorInterface {
    fun getSineWave(samples: Int, sampleRate: Int, frequencyOfTone: Double): DoubleArray
    fun get16BitPcm(samples: DoubleArray): ByteArray
    fun createPlayer()
    fun writeSound(samples: DoubleArray)
    fun destroyAudioTrack()
}