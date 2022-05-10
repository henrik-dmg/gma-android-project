package htw.gma_sose22.metronomprokit;

import android.media.AudioTrack;

public interface MetronomeInterface {

    int getBPM();

    void setBPM(int speed);

    int getAccuracy();

    void setAccuracy(int accuracy);

    void start();

    void stop();

    void togglePlayback();

    boolean getIsPlaying();

    byte[] getSound();

    void setSound(byte[] sound);

    AudioTrack getAudioTrack();

    void setAudioTrack(AudioTrack audioTrack);

}
