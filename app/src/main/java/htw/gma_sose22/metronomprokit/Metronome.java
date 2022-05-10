package htw.gma_sose22.metronomprokit;

import java.util.Random;

import android.media.AudioTrack;

public class Metronome implements MetronomeInterface {

    public static final int DEFAULT_SPEED = 100;
    public static final int MAX_ACCURACY = 100;
    public static final int MAX_LAG_BEATS = 1;

    private int bpm, accuracy;
    private boolean isPlaying = false;
    private byte[] sound;
    private AudioTrack audioTrack;
    private Runnable playbackRunnable;
    private Thread playbackThread;

    public Metronome(byte[] sound, AudioTrack audioTrack) {
        this(DEFAULT_SPEED, sound, audioTrack);
    }

    public Metronome(int speed, byte[] sound, AudioTrack audioTrack) {
        this(speed, MAX_ACCURACY, sound, audioTrack);
    }

    public Metronome(int speed, int accuracy, byte[] sound, AudioTrack audioTrack) {
        this.setAccuracy(accuracy);
        this.setSound(sound);
        this.setBPM(speed);
        this.setAudioTrack(audioTrack);
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public void setBPM(int speed) {
        this.bpm = speed;
    }

    @Override
    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    private byte[] buildSpace(int beatLength, int soundLength) {
        int error = 0;
        if(this.accuracy < MAX_ACCURACY) {
            int errorRandom = new Random().nextInt(MAX_ACCURACY-1) + 1; //Generate a random number in the range 1 to MAX_ACCURACY
            if(errorRandom > this.accuracy) {
                //A mistake is going to occur
                double errorRangeFactor = (double)(errorRandom - this.accuracy)/(double)MAX_ACCURACY;
                double flip = new Random().nextDouble();
                // 50/50 chance that the mistake is playing early or late
                if(flip < 0.5 && beatLength > soundLength) {
                    //Too little space
                    error = (int) (-1 * Math.round(errorRangeFactor * (beatLength - soundLength)));
                    this.bpm++;
                } else {
                    //Too much space
                    error = (int)(Math.round(errorRangeFactor * soundLength * MAX_LAG_BEATS));
                    this.bpm--;
                }
            }
        }
        int spaceLength = beatLength - soundLength + error;
        byte[] space = new byte[spaceLength];
        return space;
    }

    @Override
    public void start() {
        audioTrack.play();
        isPlaying = true;

        playbackRunnable = () -> {
            while (isPlaying) {
                int beatLength = (int) Math.round((60.0/bpm)*audioTrack.getSampleRate());
                beatLength = beatLength * 2;
                int soundLength = sound.length;
                if(soundLength > beatLength)
                    soundLength = beatLength; //with higher BPMs, the full sound is too long
                audioTrack.write(sound, 0, soundLength);
                byte[] space = buildSpace(beatLength, soundLength);
                audioTrack.write(space, 0, space.length);
            }
        };

        playbackThread = new Thread(playbackRunnable);
        playbackThread.start();
    }

    @Override
    public void stop() {
        audioTrack.pause();
        audioTrack.flush();
        isPlaying = false;
    }

    @Override
    public void togglePlayback() {
        if (isPlaying) {
            stop();
        } else {
            start();
        }
    }

    @Override
    public boolean getIsPlaying() {
        return isPlaying;
    }

    @Override
    public byte[] getSound() {
        return sound;
    }

    @Override
    public void setSound(byte[] sound) {
        this.sound = sound;
    }

    @Override
    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    @Override
    public void setAudioTrack(AudioTrack audioTrack) {
        this.audioTrack = audioTrack;
    }

}