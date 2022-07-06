package htw.gma_sose22.metronomeui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomekit.database.BeatPatternRepository
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: BeatPatternRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPatterns: LiveData<List<BeatPattern>> = repository.allPatterns.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertPattern(pattern: BeatPattern) = viewModelScope.launch {
        repository.insert(pattern)
    }

    fun removePattern(index: Int) = viewModelScope.launch {
        allPatterns.value?.get(index)?.let { pattern ->
            repository.delete(pattern)
        }
    }

    fun restorePattern(pattern: BeatPattern?, index: Int) = viewModelScope.launch {
        pattern?.let { pattern ->
            insertPattern(pattern)
        }
    }

}