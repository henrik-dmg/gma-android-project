package htw.gma_sose22.metronompro.ui.metronome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MetronomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MetronomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}