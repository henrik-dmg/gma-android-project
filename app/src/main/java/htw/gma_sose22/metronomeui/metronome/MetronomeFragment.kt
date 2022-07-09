package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import htw.gma_sose22.R
import htw.gma_sose22.databinding.FragmentMetronomeBinding
import htw.gma_sose22.metronomekit.beat.Beat
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
        binding.beatView.tonesView.bind(viewModel)

        viewModel.beat.observe(viewLifecycleOwner) {
            updateBeatView(it)
        }
        viewModel.beat.value?.let {
            updateBeatView(it)
        }

        setupBindings()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBindings() {
        val startStopButton = binding.buttonStartStop
        viewModel?.isPlaying?.observe(viewLifecycleOwner) { isPlaying ->
            toggleMetronomeControls(!isPlaying)
            if (isPlaying) {
                startStopButton.text = resources.getText(R.string.metronome_stop_button_title)
            } else {
                startStopButton.text = resources.getText(R.string.metronome_start_button_title)
                binding.beatView.tonesView.unhighlightToneButton()
            }
        }
        startStopButton.setOnClickListener {
            MetronomeService.configureToneChangeHandler(this)
            viewModel?.handleStartStopButtonClicked()
        }
    }

    private fun toggleMetronomeControls(controlsEnabled: Boolean) {
        binding.beatView.bpmModificationView.toggleControls(controlsEnabled)
        binding.beatView.tonesView.toggleControls(controlsEnabled)
    }

    private fun updateBeatView(beat: Beat) {
        binding.beatView.bpmModificationView.updateView(beat)
        binding.beatView.tonesView.updateView(beat)
    }

    override fun currentToneChanged(toneIndex: Int, beatIndex: Int) {
        binding.beatView.tonesView.highlightToneButton(toneIndex)
    }

    override fun playbackStopped() {
        binding.beatView.tonesView.unhighlightToneButton()
        viewModel?.playbackStopped()
    }

}