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
import htw.gma_sose22.metronomekit.beat.ToneChangeHandler
import htw.gma_sose22.metronomekit.metronome.MetronomeService

class MetronomeFragment : Fragment(), ToneChangeHandler {

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
        val viewModel = ViewModelProvider(this)[MetronomeViewModel::class.java]
        this.viewModel = viewModel
        _binding = FragmentMetronomeBinding.inflate(inflater, container, false)

        binding.beatView.bpmModificationView.bind(viewModel)

        setupBindings()
        setupMetronomeControls()
        viewModel.beat.value?.let {
            updateBeatView(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMetronomeControls() {
        binding.beatView.tonesView.tonesDecrementButton.setOnClickListener {
            viewModel?.removeNoteFromBeat()
        }

        binding.beatView.tonesView.tonesIncrementButton.setOnClickListener {
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
        startStopButton.setOnClickListener {
            MetronomeService.configureToneChangeHandler(this)
            viewModel?.handleStartStopButtonClicked()
        }
    }

    private fun toggleMetronomeControls(controlsEnabled: Boolean) {
        binding.beatView.bpmModificationView.toggleControls(controlsEnabled)
        binding.beatView.tonesView.tonesIncrementButton.isEnabled = controlsEnabled
        binding.beatView.tonesView.tonesDecrementButton.isEnabled = controlsEnabled
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

        binding.beatView.bpmModificationView.updateView(beat)

        binding.beatView.tonesView.tonesIncrementButton.isEnabled = beat.canAddNote
        binding.beatView.tonesView.tonesDecrementButton.isEnabled = beat.canRemoveNote
        binding.beatView.tonesView.tonesTextView.text = resources.getString(R.string.tones_count, beat.noteCount.toInt())
    }

    private fun updateButtonImage(button: MaterialButton, tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon = resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon = resources.getDrawable(R.drawable.ic_note_default)
        }
    }

    override fun currentToneChanged(toneIndex: Int, beatIndex: Int) {
        // TODO: Call view to highlight here
    }

    override fun playbackStopped() {
        viewModel?.playbackStopped()
    }

}