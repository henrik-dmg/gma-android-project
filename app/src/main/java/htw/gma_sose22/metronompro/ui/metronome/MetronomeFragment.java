package htw.gma_sose22.metronompro.ui.metronome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import htw.gma_sose22.metronompro.databinding.FragmentMetronomeBinding;
import htw.gma_sose22.metronomprokit.MetronomeService;

public class MetronomeFragment extends Fragment {

    private FragmentMetronomeBinding binding;
    private Button startStopButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MetronomeViewModel metronomeViewModel = new ViewModelProvider(this).get(MetronomeViewModel.class);

        binding = FragmentMetronomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button startStopButton = binding.buttonStartStop;
        metronomeViewModel.getText().observe(getViewLifecycleOwner(), startStopButton::setText);

        setupStartStopButton();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupStartStopButton() {
        startStopButton = binding.buttonStartStop;
        startStopButton.setOnClickListener((button) -> {
            handleStartStopButtonClicked();
        });
    }

    private void handleStartStopButtonClicked() {
        MetronomeService service = MetronomeService.getSharedInstance();
        service.togglePlayback();
    }

}