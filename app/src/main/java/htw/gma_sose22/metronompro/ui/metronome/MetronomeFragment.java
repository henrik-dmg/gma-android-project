package htw.gma_sose22.metronompro.ui.metronome;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executors;

import htw.gma_sose22.metronompro.databinding.FragmentMetronomeBinding;
import htw.gma_sose22.metronomprokit.Metronome;
import htw.gma_sose22.metronomprokit.MetronomeCallable;
import htw.gma_sose22.metronomprokit.MetronomeInterface;

public class MetronomeFragment extends Fragment {

    private FragmentMetronomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MetronomeViewModel metronomeViewModel = new ViewModelProvider(this).get(MetronomeViewModel.class);

        binding = FragmentMetronomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        metronomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        MetronomeInterface metronome = new Metronome(handler);
        metronome.setBpm(120);

        MetronomeCallable metronomeCallable = new MetronomeCallable(metronome);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                metronomeCallable.call();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

}