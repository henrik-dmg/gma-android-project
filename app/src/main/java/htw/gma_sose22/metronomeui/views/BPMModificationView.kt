package htw.gma_sose22.metronomeui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.BpmModificationViewBinding
import htw.gma_sose22.metronomekit.beat.Beat

class BPMModificationView : LinearLayout {

    private var bpmModifiable: BPMModifiable? = null

    private val binding: BpmModificationViewBinding

    private val bpmTextView: TextView
        get() = binding.bpmTextView
    private val smallBpmDecrementButton: MaterialButton
        get() = binding.smallBpmDecrementButton
    private val largeBpmDecrementButton: MaterialButton
        get() = binding.largeBpmDecrementButton
    private val smallBpmIncrementButton: MaterialButton
        get() = binding.smallBpmIncrementButton
    private val largeBpmIncrementButton: MaterialButton
        get() = binding.largeBpmIncrementButton

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BpmModificationViewBinding.inflate(inflater, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.smallBpmDecrementButton.setOnClickListener {
            bpmModifiable?.modifyBPM(-1)
        }
        binding.largeBpmDecrementButton.setOnClickListener {
            bpmModifiable?.modifyBPM(-10)
        }
        binding.smallBpmIncrementButton.setOnClickListener {
            bpmModifiable?.modifyBPM(1)
        }
        binding.largeBpmIncrementButton.setOnClickListener {
            bpmModifiable?.modifyBPM(10)
        }
    }

    fun bind(bpmModifiable: BPMModifiable) {
        this.bpmModifiable = bpmModifiable
    }

    fun updateView(beat: Beat) {
        bpmTextView.text = context.resources.getString(R.string.bpm_count, beat.tempo)
        smallBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        largeBpmIncrementButton.isEnabled = beat.canIncreaseBPM
        smallBpmDecrementButton.isEnabled = beat.canDecreaseBPM
        largeBpmDecrementButton.isEnabled = beat.canDecreaseBPM
    }

    fun toggleControls(controlsEnabled: Boolean) {
        smallBpmIncrementButton.isEnabled = controlsEnabled
        largeBpmIncrementButton.isEnabled = controlsEnabled
        smallBpmDecrementButton.isEnabled = controlsEnabled
        largeBpmDecrementButton.isEnabled = controlsEnabled
    }

}