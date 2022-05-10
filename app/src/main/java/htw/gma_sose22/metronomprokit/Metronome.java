package htw.gma_sose22.metronomprokit;

import android.os.Handler;
import android.os.Message;

public class Metronome implements MetronomeInterface {

    private double bpm = 100;
    private int beat;
    private int noteValue = 4;
    private int silence;

    private double beatSound = 2440;
    private double sound = 6440;
    private final int tick = 1000; // samples of tick

    private boolean play = true;

    private final AudioGeneratorInterface audioGenerator;
    private final Handler messageHandler;
    private double[] soundTickArray;
    private double[] soundTockArray;
    private double[] silenceSoundArray;
    private Message msg;
    private int currentBeat = 1;

    public Metronome(Handler handler) {
        this.audioGenerator = new AudioGenerator(8000);
        audioGenerator.createPlayer();
        this.messageHandler = handler;
    }

    public void calculateSilence() {
        silence = (int) (((60/bpm)*8000)-tick);
        soundTickArray = new double[this.tick];
        soundTockArray = new double[this.tick];
        silenceSoundArray = new double[this.silence];
        msg = new Message();
        msg.obj = ""+currentBeat;
        double[] tick = audioGenerator.getSineWave(this.tick, 8000, beatSound);
        double[] tock = audioGenerator.getSineWave(this.tick, 8000, sound);
        for(int i=0;i<this.tick;i++) {
            soundTickArray[i] = tick[i];
            soundTockArray[i] = tock[i];
        }
        for(int i = 0; i < silence; i++) {
            silenceSoundArray[i] = 0;
        }
    }

    public void play() {
        calculateSilence();
        do {
            msg = new Message();
            msg.obj = ""+currentBeat;
            if(currentBeat == 1)
                audioGenerator.writeSound(soundTockArray);
            else
                audioGenerator.writeSound(soundTickArray);
            if(bpm <= 120)
                messageHandler.sendMessage(msg);
            audioGenerator.writeSound(silenceSoundArray);
            if(bpm > 120)
                messageHandler.sendMessage(msg);
            currentBeat++;
            if(currentBeat > beat)
                currentBeat = 1;
        } while(play);
    }

    public void stop() {
        play = false;
        audioGenerator.destroyAudioTrack();
    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(int bpmetre) {
        this.noteValue = bpmetre;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public double getBeatSound() {
        return beatSound;
    }

    public void setBeatSound(double sound1) {
        this.beatSound = sound1;
    }

    public double getSound() {
        return sound;
    }

    public void setSound(double sound2) {
        this.sound = sound2;
    }

}