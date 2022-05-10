package htw.gma_sose22.metronompro.ui.editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import htw.gma_sose22.metronompro.databinding.FragmentEditorBinding;

public class EditorFragment extends Fragment {

    private FragmentEditorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditorViewModel editorViewModel = new ViewModelProvider(this).get(EditorViewModel.class);

        binding = FragmentEditorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button startStopButton = binding.buttonStartStop;
        editorViewModel.getText().observe(getViewLifecycleOwner(), startStopButton::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    
}