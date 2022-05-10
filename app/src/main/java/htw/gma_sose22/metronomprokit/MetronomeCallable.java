package htw.gma_sose22.metronomprokit;

import java.util.concurrent.Callable;

public class MetronomeCallable implements Callable<MetronomeInterface> {

    MetronomeInterface metronome;

    public MetronomeCallable(MetronomeInterface metronome) {
        this.metronome = metronome;
    }

    public void stop() {
        metronome.stop();
        metronome = null;
    }

    public void setBpm(short bpm) {
        metronome.setBpm(bpm);
        metronome.calculateSilence();
    }

    public void setBeat(short beat) {
        if(metronome != null) {
            metronome.setBeat(beat);
        }
    }

    @Override
    public MetronomeInterface call() throws Exception {
        metronome.play();
        return metronome;
    }

}
