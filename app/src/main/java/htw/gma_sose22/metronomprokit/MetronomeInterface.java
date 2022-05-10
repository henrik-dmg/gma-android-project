package htw.gma_sose22.metronomprokit;

public interface MetronomeInterface {

    void calculateSilence();
    void play();
    void stop();

    double getBpm();
    void setBpm(int bpm);

    int getNoteValue();
    void setNoteValue(int bpmetre);

    int getBeat();
    void setBeat(int beat);

    double getBeatSound();
    void setBeatSound(double sound1);

    double getSound();
    void setSound(double sound2);

}
