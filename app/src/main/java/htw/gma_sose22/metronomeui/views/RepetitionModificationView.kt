package htw.gma_sose22.metronomeui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import htw.gma_sose22.R
import htw.gma_sose22.databinding.RepetitionsModificationViewBinding
import htw.gma_sose22.metronomekit.beat.Beat
import java.lang.ref.WeakReference

class RepetitionModificationView : LinearLayout, BeatModificationView<RepetitionModifiable> {

    private var repetitionModifiable: WeakReference<RepetitionModifiable> = WeakReference(null)
    private val binding: RepetitionsModificationViewBinding

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RepetitionsModificationViewBinding.inflate(inflater, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        binding.repetitionIncrementButton.setOnClickListener {
            repetitionModifiable.get()?.addRepetition()
        }
        binding.repetitionDecrementButton.setOnClickListener {
            repetitionModifiable.get()?.removeRepetition()
        }
    }

    override fun bind(modifiable: RepetitionModifiable) {
        this.repetitionModifiable = WeakReference(modifiable)
    }

    override fun unbind() {
        this.repetitionModifiable.clear()
    }

    override fun updateView(beat: Beat) {
        beat.repetitions?.let {
            binding.repetitionTextView.text = context.resources.getString(R.string.repetitions_count, it.toInt())
        }
        binding.repetitionIncrementButton.isEnabled = beat.canIncreaseRepetitions
        binding.repetitionDecrementButton.isEnabled = beat.canDecreaseRepetitions
    }

}