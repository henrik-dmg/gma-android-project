package htw.gma_sose22.metronomprokit

import android.media.*
import kotlin.experimental.*
import kotlin.math.sin

class AudioGenerator(private val sampleRate: Int) : AudioGeneratorInterface {

    private var audioTrack: AudioTrack? = null

    override fun getSineWave(samples: Int, sampleRate: Int, frequencyOfTone: Double): DoubleArray {
        val sample = DoubleArray(samples)
        for (i in 0 until samples) {
            sample[i] = sin(2 * Math.PI * i / (sampleRate / frequencyOfTone))
        }
        return sample
    }

    override fun get16BitPcm(samples: DoubleArray): ByteArray {
        val generatedSound = ByteArray(2 * samples.size)
        var index = 0
        for (sample in samples) {
            // scale to maximum amplitude
            val maxSample = (sample * Short.MAX_VALUE.toInt()).toInt()
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSound[index++] = (maxSample and 0x00ff).toByte()
            generatedSound[index++] = (maxSample and 0xff00).ushr(8).toByte()
        }
        return generatedSound
    }

    override fun createPlayer() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val audioFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(sampleRate)
            .build()
        audioTrack?.play()
    }

    override fun writeSound(samples: DoubleArray) {
        val generatedSound = get16BitPcm(samples)
        audioTrack?.write(generatedSound, 0, generatedSound.size)
    }

    override fun destroyAudioTrack() {
        audioTrack?.stop()
        audioTrack?.release()
    }
}