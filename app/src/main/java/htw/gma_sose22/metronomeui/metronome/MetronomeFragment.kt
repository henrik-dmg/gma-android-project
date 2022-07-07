package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.FragmentMetronomeBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.Tone

class MetronomeFragment : Fragment() {

    private var _binding: FragmentMetronomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var viewModel: MetronomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MetronomeViewModel::class.java]
        _binding = FragmentMetronomeBinding.inflate(inflater, container, false)

        setupBindings()
        setupMetronomeControls()
        viewModel?.beat?.value?.let {
            updateBeatView(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMetronomeControls() {
        binding.beatView.bpmModificationView.smallBpmDecrementButton.setOnClickListener {
            viewModel?.modifyBPM(-1)
        }
        binding.beatView.bpmModificationView.largeBpmDecrementButton.setOnClickListener {
            viewModel?.modifyBPM(-10)
        }
        binding.beatView.bpmModificationView.smallBpmIncrementButton.setOnClickListener {
            viewModel?.modifyBPM(1)
        }
        binding.beatView.bpmModificationView.largeBpmIncrementButton.setOnClickListener {
            viewModel?.modifyBPM(10)
        }

        binding.beatView.tonesView.decrementNotesButton.setOnClickListener {
            viewModel?.removeNoteFromBeat()
        }

        binding.beatView.tonesView.incrementNotesButton.setOnClickListener {
            viewModel?.addNoteToBeat()
        }

        for (i in 0 until binding.beatView.tonesView.beatButtons.childCount) {
            val button = binding.beatView.tonesView.beatButtons.getChildAt(i) as MaterialButton
            button.setOnClickListener {
                Log.d("MetronomeFragment", "button $i clicked")
                viewModel?.rotateNoteTypeAtIndex(i.toUInt())
            }
        }
    }

    private fun setupBindings() {
        viewModel?.beat?.observe(viewLifecycleOwner) { beat ->
            Log.d("MetronomeFragment", "$beat was modified")
            updateBeatView(beat)
        }

        val startStopButton = binding.buttonStartStop
        viewModel?.isPlaying?.observe(viewLifecycleOwner) { isPlaying ->
            toggleMetronomeControls(!isPlaying)
            if (isPlaying) {
                startStopButton.text = resources.getText(R.string.metronome_stop_button_title)
            } else {
                startStopButton.text = resources.getText(R.string.metronome_start_button_title)
            }
        }
        startStopButton.setOnClickListener { viewModel?.handleStartStopButtonClicked() }
    }

    private fun toggleMetronomeControls(controlsEnabled: Boolean) {
        binding.beatView.bpmModificationView.smallBpmIncrementButton.isEnabled = controlsEnabled
        binding.beatView.bpmModificationView.smallBpmDecrementButton.isEnabled = controlsEnabled
        binding.beatView.bpmModificationView.largeBpmIncrementButton.isEnabled = controlsEnabled
        binding.beatView.bpmModificationView.largeBpmDecrementButton.isEnabled = controlsEnabled
        binding.beatView.tonesView.decrementNotesButton.isEnabled = controlsEnabled
        binding.beatView.tonesView.incrementNotesButton.isEnabled = controlsEnabled
    }

    private fun updateBeatView(beat: Beat) {
        val beatButtons = binding.beatView.tonesView.beatButtons
        val currentNumberOfButtons = beatButtons.childCount

        val tones = beat.makeNotes()

        for (i in 0 until currentNumberOfButtons) {
            val button = beatButtons.getChildAt(i)
            if (i < tones.size) {
                button.visibility = View.VISIBLE
                updateButtonImage(button as MaterialButton, tones[i])
            } else {
                button.visibility = View.GONE
            }
        }

        binding.beatView.bpmModificationView.bpmTextView.text = resources.getString(R.string.bpm_count, beat.tempo)
        binding.beatView.tonesView.incrementNotesButton.isEnabled = beat.canAddNote
        binding.beatView.tonesView.decrementNotesButton.isEnabled = beat.canRemoveNote
        binding.beatView.bpmModificationView.smallBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        binding.beatView.bpmModificationView.largeBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        binding.beatView.bpmModificationView.smallBpmDecrementButton.isEnabled = beat.canDecreaseBPM
        binding.beatView.bpmModificationView.largeBpmDecrementButton.isEnabled = beat.canDecreaseBPM
        binding.beatView.tonesView.noteCountLabel.text = resources.getQuantityString(
            R.plurals.notes_count,
            beat.noteCount.toInt(),
            beat.noteCount.toInt()
        )
    }

    private fun updateButtonImage(button: MaterialButton, tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon = resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon = resources.getDrawable(R.drawable.ic_note_default)
        }
    }

}