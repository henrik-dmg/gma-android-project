package htw.gma_sose22.metronomeui.metronome

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SeekBar
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
            bpmLabel.setText(String.format("%d", bpm))
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
        binding.metronomeBPMLabel.setOnFocusChangeListener { view, hasFocus ->
            Log.d("MetronomeFragment", "Metronome Label entered text")
            if (!hasFocus) {
                val enteredText = (view as EditText).text
                val bpmValue = enteredText.toString().toInt()
                Log.d("MetronomeFragment", "Changed BPM to $bpmValue")
            }
        }

        binding.bpmSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

}