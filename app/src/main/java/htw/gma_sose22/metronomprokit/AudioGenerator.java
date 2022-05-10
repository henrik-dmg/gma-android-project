package htw.gma_sose22.metronomprokit;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

public class AudioGenerator implements AudioGeneratorInterface {

    private int sampleRate;
    private AudioTrack audioTrack;

    public AudioGenerator(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    @Override
    public double[] getSineWave(int samples, int sampleRate, double frequencyOfTone) {
        double[] sample = new double[samples];
        for (int i = 0; i < samples; i++) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/frequencyOfTone));
        }
        return sample;
    }

    @Override
    public byte[] get16BitPcm(double[] samples) {
        byte[] generatedSound = new byte[2 * samples.length];
        int index = 0;
        for (double sample : samples) {
            // scale to maximum amplitude
            short maxSample = (short) ((sample * Short.MAX_VALUE));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSound[index++] = (byte) (maxSample & 0x00ff);
            generatedSound[index++] = (byte) ((maxSample & 0xff00) >>> 8);

        }
        return generatedSound;
    }

    @Override
    public void createPlayer() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build();

        audioTrack = new AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(sampleRate)
                .build();

        audioTrack.play();
    }

    @Override
    public void writeSound(double[] samples) {
        byte[] generatedSnd = get16BitPcm(samples);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
    }

    @Override
    public void destroyAudioTrack() {
        audioTrack.stop();
        audioTrack.release();
    }

}