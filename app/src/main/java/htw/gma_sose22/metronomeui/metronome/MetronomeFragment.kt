package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        viewModel?.bpm?.observe(viewLifecycleOwner) { bpm: Int? ->
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
        startStopButton.setOnClickListener { viewModel?.handleStartStopButtonClicked() }
    }

    private fun setupMetronomeControls() {
        val smallDecrementButton = binding.bpmModificationView.metronomeSmallDecrementButton
        smallDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -1
            )
        }
        val smallIncrementButton = binding.bpmModificationView.metronomeSmallIncrementButton
        smallIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                1
            )
        }
        val mediumDecrementButton = binding.bpmModificationView.metronomeMediumDecrementButton
        mediumDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -5
            )
        }
        val mediumIncrementButton = binding.bpmModificationView.metronomeMediumIncrementButton
        mediumIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                5
            )
        }
        val largeDecrementButton = binding.bpmModificationView.metronomeLargeDecrementButton
        largeDecrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                -10
            )
        }
        val largeIncrementButton = binding.bpmModificationView.metronomeLargeIncrementButton
        largeIncrementButton.setOnClickListener {
            viewModel?.handleBPMChangeRequested(
                10
            )
        }
    }

}