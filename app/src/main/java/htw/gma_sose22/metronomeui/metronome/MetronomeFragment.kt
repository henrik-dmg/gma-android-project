package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SeekBar
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
        binding.metronomeBeatView.bpmModificationView.metronomeBPMLabel.setOnFocusChangeListener { view, hasFocus ->
            Log.d("MetronomeFragment", "Metronome Label entered text")
            if (!hasFocus) {
                val enteredText = (view as EditText).text
                val bpmValue = enteredText.toString().toInt()
                Log.d("MetronomeFragment", "Changed BPM to $bpmValue")
            }
        }

        binding.metronomeBeatView.beatView.decrementNotesButton.setOnClickListener {
            viewModel?.removeNoteFromBeat()
        }

        binding.metronomeBeatView.beatView.incrementNotesButton.setOnClickListener {
            viewModel?.addNoteToBeat()
        }

        for (i in 0 until binding.metronomeBeatView.beatView.beatButtons.childCount) {
            val button = binding.metronomeBeatView.beatView.beatButtons.getChildAt(i) as MaterialButton
            button.setOnClickListener {
                Log.d("MetronomeFragment", "button $i clicked")
                viewModel?.rotateNoteTypeAtIndex(i)
            }
        }

        binding.metronomeBeatView.bpmModificationView.bpmSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel?.setBPMMappedToAllowedRange(progress.toDouble() / 100.0)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })
    }

    private fun setupBindings() {
        val bpmLabel = binding.metronomeBeatView.bpmModificationView.metronomeBPMLabel
        viewModel?.bpm?.observe(viewLifecycleOwner) { bpm ->
            bpmLabel.setText(String.format("%d", bpm))
        }

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
        binding.metronomeBeatView.bpmModificationView.metronomeBPMLabel.isEnabled = controlsEnabled
        binding.metronomeBeatView.bpmModificationView.bpmSeekBar.isEnabled = controlsEnabled
        binding.metronomeBeatView.beatView.decrementNotesButton.isEnabled = controlsEnabled
        binding.metronomeBeatView.beatView.incrementNotesButton.isEnabled = controlsEnabled
    }

    private fun updateBeatView(beat: Beat) {
        val beatButtons = binding.metronomeBeatView.beatView.beatButtons
        val currentNumberOfButtons = beatButtons.childCount

        val tones = beat.makeNotes()

        for (i in 0 until currentNumberOfButtons) {
            val button = beatButtons.getChildAt(i)
            if (i < beat.noteCount) {
                button.visibility = View.VISIBLE
                updateButtonImage(button as MaterialButton, tones[i])
            } else {
                button.visibility = View.GONE
            }
        }

        binding.metronomeBeatView.beatView.noteCountLabel.text = resources.getQuantityString(R.plurals.notes_count, beat.noteCount, beat.noteCount)
        viewModel?.mappedBPM?.let {
            Log.d("MetronomeFragment", "Changing seekbar progress to $it")
            binding.metronomeBeatView.bpmModificationView.bpmSeekBar.progress = it
        }
    }

    private fun updateButtonImage(button: MaterialButton, tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon = resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon = resources.getDrawable(R.drawable.ic_note_default)
        }
    }

}