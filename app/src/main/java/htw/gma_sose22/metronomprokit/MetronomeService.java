package htw.gma_sose22.metronomprokit;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class MetronomeService {

    private static MetronomeService sharedInstance;

    private MetronomeInterface metronome;
    private AudioTrack audioTrack;
    private byte[] metronomeSound;

    private MetronomeService() {}

    public static MetronomeService getSharedInstance() throws NullPointerException {
        if (sharedInstance == null) {
            sharedInstance = new MetronomeService();
        }
        return sharedInstance;
    }

    public static void setSharedInstance(byte[] metronomeSound) {
        MetronomeService metronomeService = getSharedInstance();
        metronomeService.setMetronomeSound(metronomeSound);
    }

    public MetronomeInterface getMetronome() {
        if (metronome == null) {
            metronome = makeMetronome();
        }
        return metronome;
    }

    private MetronomeInterface makeMetronome() {
        return new Metronome(100, 100, metronomeSound, getAudioTrack());
    }

    public AudioTrack getAudioTrack() {
        if (audioTrack == null) {
            audioTrack = makeAudioTrack();
        }
        return audioTrack;
    }

    private AudioTrack makeAudioTrack() {
//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .build();
//
//        AudioFormat audioFormat = new AudioFormat.Builder()
//                .setEncoding(AudioFormat.ENCODING_PCM_8BIT)
//                .setSampleRate(44100)
//                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
//                .build();
//
//        return new AudioTrack.Builder()
//                .setAudioAttributes(audioAttributes)
//                .setAudioFormat(audioFormat)
//                .setBufferSizeInBytes(44100)
//                .build();

        return new AudioTrack(
                AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                44100,
                AudioTrack.MODE_STREAM
        );
    }

    public void startMetronome() {
        MetronomeInterface metronome = getMetronome();
        if (metronome.getIsPlaying()) {
            Log.e("MetronomeService", "Metronome is already playing");
        } else {
            metronome.start();
        }
    }

    public void stopMetronome() {
        MetronomeInterface metronome = getMetronome();
        if (metronome.getIsPlaying()) {
            metronome.stop();
        } else {
            Log.e("MetronomeService", "Metronome is not playing");
        }
    }

    void setMetronomeSound(byte[] metronomeSound) {
        this.metronomeSound = metronomeSound;
    }

}
