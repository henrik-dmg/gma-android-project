package htw.gma_sose22.metronomprokit.audio

interface AudioWriteable {
    val sampleRate: Int

    fun write(audioData: ByteArray, offsetInBytes: Int, sizeInBytes: Int): Int
}