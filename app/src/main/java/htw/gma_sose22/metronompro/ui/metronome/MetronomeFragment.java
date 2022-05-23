package htw.gma_sose22.metronompro.ui.metronome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import htw.gma_sose22.metronompro.databinding.FragmentMetronomeBinding;

public class MetronomeFragment extends Fragment {

    private MetronomeViewModel metronomeViewModel;
    private FragmentMetronomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        metronomeViewModel = new ViewModelProvider(this).get(MetronomeViewModel.class);

        binding = FragmentMetronomeBinding.inflate(inflater, container, false);

        final TextView bpmLabel = binding.metronomeBPMLabel;
        metronomeViewModel.getBpm().observe(getViewLifecycleOwner(), (bpm) -> bpmLabel.setText(String.format("%d", bpm)));

        setupStartStopButton();
        setupMetronomeControls();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupStartStopButton() {
        Button startStopButton = binding.buttonStartStop;
        startStopButton.setOnClickListener((button) -> metronomeViewModel.handleStartStopButtonClicked());
    }

    private void setupMetronomeControls() {
        Button smallDecrementButton = binding.metronomeSmallDrecrementButton;
        smallDecrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(-1));
        Button smallIncrementButton = binding.metronomeSmallIncrementButton;
        smallIncrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(1));

        Button mediumDecrementButton = binding.metronomeMediumDrecrementButton;
        mediumDecrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(-5));
        Button mediumIncrementButton = binding.metronomeMediumIncrementButton;
        mediumIncrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(5));

        Button largeDecrementButton = binding.metronomeLargeDrecrementButton;
        largeDecrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(-10));
        Button largeIncrementButton = binding.metronomeLargeIncrementButton;
        largeIncrementButton.setOnClickListener((button) -> metronomeViewModel.handleBPMChangeRequested(10));
    }

}