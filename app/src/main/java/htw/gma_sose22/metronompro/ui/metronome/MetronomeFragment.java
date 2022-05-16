package htw.gma_sose22.metronompro.ui.metronome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import htw.gma_sose22.metronompro.databinding.FragmentMetronomeBinding;
import htw.gma_sose22.metronomprokit.metronome.MetronomeService;

public class MetronomeFragment extends Fragment {

    private FragmentMetronomeBinding binding;
    private Button startStopButton;
    private Button smallIncrementButton, smallDecrementButton;
    private Button mediumIncrementButton, mediumDecrementButton;
    private Button largeIncrementButton, largeDecrementButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MetronomeViewModel metronomeViewModel = new ViewModelProvider(this).get(MetronomeViewModel.class);

        binding = FragmentMetronomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button startStopButton = binding.buttonStartStop;
        metronomeViewModel.getText().observe(getViewLifecycleOwner(), startStopButton::setText);

        setupStartStopButton();
        setupMetronomeControls();
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

    private void setupMetronomeControls() {
        smallDecrementButton = binding.metronomeSmallDrecrementButton;
        smallDecrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(-1);
        });
        smallIncrementButton = binding.metronomeSmallIncrementButton;
        smallIncrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(1);
        });

        mediumDecrementButton = binding.metronomeMediumDrecrementButton;
        mediumDecrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(-5);
        });
        mediumIncrementButton = binding.metronomeMediumIncrementButton;
        mediumIncrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(5);
        });

        largeDecrementButton = binding.metronomeLargeDrecrementButton;
        largeDecrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(-10);
        });
        largeIncrementButton = binding.metronomeLargeIncrementButton;
        largeIncrementButton.setOnClickListener((button) -> {
            handleBPMChangeRequested(10);
        });
    }

    private void handleBPMChangeRequested(int bpmDelta) {
        MetronomeService.INSTANCE.changeBPM(bpmDelta);
    }

    private void handleStartStopButtonClicked() {
        MetronomeService.INSTANCE.togglePlayback();
    }

}