package htw.gma_sose22.metronompro.ui.notifications;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Executable;
import java.util.concurrent.Executors;

import htw.gma_sose22.metronompro.databinding.FragmentNotificationsBinding;
import htw.gma_sose22.metronomprokit.*;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private MetronomeAsyncTask metroTask;
    private TaskRunner taskRunner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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

        MetronomeAsyncTask metronomeAsyncTask = new MetronomeAsyncTask(metronome);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                metronomeAsyncTask.call();
            } catch (Exception e) {

            }
        });

//        if(buttonText.equalsIgnoreCase("start")) {
//
//        } else {
//            button.setText(R.string.start);
//            metroTask.stop();
//
//            Runtime.getRuntime().gc();
//        }
    }

}