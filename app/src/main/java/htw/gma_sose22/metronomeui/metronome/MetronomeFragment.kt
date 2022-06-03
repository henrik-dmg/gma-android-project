package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import htw.gma_sose22.metronomepro.R
import htw.gma_sose22.metronomepro.databinding.FragmentMetronomeBinding

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

        val bpmLabel = binding.metronomeBPMLabel
        viewModel?.bpm?.observe(viewLifecycleOwner) { bpm ->
            bpmLabel.text = String.format("%d", bpm)
        }

        setupStartStopButton()
        setupMetronomeControls()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupStartStopButton() {
        val startStopButton = binding.buttonStartStop
        viewModel?.isPlaying?.observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying) {
                startStopButton.text = resources.getText(R.string.metronome_stop_button_title)
            } else {
                startStopButton.text = resources.getText(R.string.metronome_start_button_title)
            }
        }
        startStopButton.setOnClickListener { viewModel?.handleStartStopButtonClicked() }
    }

    private fun setupMetronomeControls() {
        val smallDecrementButton = binding.metronomeSmallDrecrementButton
        smallDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -1
            )
        }
        val smallIncrementButton = binding.metronomeSmallIncrementButton
        smallIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                1
            )
        }
        val mediumDecrementButton = binding.metronomeMediumDrecrementButton
        mediumDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -5
            )
        }
        val mediumIncrementButton = binding.metronomeMediumIncrementButton
        mediumIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                5
            )
        }
        val largeDecrementButton = binding.metronomeLargeDrecrementButton
        largeDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -10
            )
        }
        val largeIncrementButton = binding.metronomeLargeIncrementButton
        largeIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                10
            )
        }
    }

}