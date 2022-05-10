package htw.gma_sose22.metronomprokit;

public interface AudioGeneratorInterface {

    double[] getSineWave(int samples, int sampleRate, double frequencyOfTone);

    byte[] get16BitPcm(double[] samples);

    void createPlayer();

    void writeSound(double[] samples);

    void destroyAudioTrack();

}
