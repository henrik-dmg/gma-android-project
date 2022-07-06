package htw.gma_sose22.metronomepro;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.io.InputStream;

import htw.gma_sose22.metronomekit.beat.BeatManager;
import htw.gma_sose22.databinding.ActivityMainBinding;
import htw.gma_sose22.R;
import htw.gma_sose22.metronomekit.metronome.*;
import kotlin.UInt;

public class MainActivity extends AppCompatActivity {

    // MARK: Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_metronome,
                R.id.navigation_library
        ).build();

        NavHostFragment navHostFragment = binding.navHostFragment.getFragment();
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        configureMetronome();
    }

    @Override
    public void onPause() {
        super.onPause();
        MetronomeService.INSTANCE.stop();
    }

    // MARK: Configuration

    private void configureMetronome() {
        byte[] beatSound = makeBeatSoundBytes();
        byte[] offbeatSound = makeOffbeatSoundBytes();
        MetronomeAudioInterface metronomeAudio = makeMetronomeAudio();
        MetronomeInterface metronome = new Metronome(Metronome.DEFAULT_SPEED, beatSound, offbeatSound, metronomeAudio, BeatManager.INSTANCE::nextTone);
        MetronomeService.INSTANCE.setMetronome(metronome);
    }

    private MetronomeAudioInterface makeMetronomeAudio() {
        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                Metronome.DEFAULT_SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                Metronome.DEFAULT_SAMPLE_RATE,
                AudioTrack.MODE_STREAM
        );
        return new WrappedAudioTrack(audioTrack);
    }

    private byte[] makeBeatSoundBytes() {
        InputStream is = this.getResources().openRawResource(R.raw.tabla_snapa);

        int WAV_INFO_BYTES = 44;
        byte[] wavInfo = new byte[WAV_INFO_BYTES];

        int TABLA_SNAP_BYTES = 17594;
        byte[] sound = new byte[TABLA_SNAP_BYTES];

        try {
            is.read(wavInfo);
            is.read(sound);
            return sound;
        } catch (IOException e) {
            Log.e("BadMetronome", "Error while reading in sound file.");
        }
        return new byte[0];
    }

    private byte[] makeOffbeatSoundBytes() {
        byte[] beatSound = makeBeatSoundBytes();
        byte[] offbeatSound = new byte[beatSound.length];
        double volumeScale = 0.2;

        for (int i = 0; i < beatSound.length; i += 2) {
            // convert byte pair to int
            short buf1 = beatSound[i + 1];
            short buf2 = beatSound[i];

            buf1 = (short) ((buf1 & 0xff) << 8);
            buf2 = (short) (buf2 & 0xff);

            short res = (short) (buf1 | buf2);
            res = (short) (res * volumeScale);

            // convert back
            offbeatSound[i] = (byte) res;
            offbeatSound[i + 1] = (byte) (res >> 8);

        }
        return offbeatSound;
    }

}