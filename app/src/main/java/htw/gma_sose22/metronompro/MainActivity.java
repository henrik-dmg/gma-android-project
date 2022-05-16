package htw.gma_sose22.metronompro;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.io.InputStream;

import htw.gma_sose22.metronompro.databinding.ActivityMainBinding;
import htw.gma_sose22.metronomprokit.metronome.MetronomeInterface;
import htw.gma_sose22.metronomprokit.metronome.MetronomeService;

public class MainActivity extends AppCompatActivity {

    // MARK: Properties

    private ActivityMainBinding binding;

    // MARK: Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_metronome,
                R.id.navigation_editor
        ).build();

        NavHostFragment navHostFragment = (NavHostFragment) binding.navHostFragmentActivityMain.getFragment();
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
        InputStream is = this.getResources().openRawResource(R.raw.tabla_snapa);

        int WAV_INFO_BYTES = 44;
        byte[] wavInfo = new byte[WAV_INFO_BYTES];

        int TABLA_SNAP_BYTES = 17594;
        byte[] sound = new byte[TABLA_SNAP_BYTES];

        try {
            is.read(wavInfo);
            is.read(sound);
        } catch (IOException e) {
            Log.e("BadMetronome", "Error while reading in sound file.");
        }

        MetronomeService.INSTANCE.configureMetronome(sound);
    }

}