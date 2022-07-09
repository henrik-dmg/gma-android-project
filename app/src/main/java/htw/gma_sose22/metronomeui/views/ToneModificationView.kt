package htw.gma_sose22.metronomeui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import htw.gma_sose22.R
import htw.gma_sose22.databinding.TonesViewBinding
import htw.gma_sose22.metronomekit.beat.Beat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class ToneModificationView : ConstraintLayout, BeatModificationView<ToneModifiable> {

    private val uiScope = MainScope()
    private var toneModifiable: WeakReference<ToneModifiable> = WeakReference(null)
    private val binding: TonesViewBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = TonesViewBinding.inflate(inflater, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.tonesIncrementButton.setOnClickListener {
            toneModifiable.get()?.addNote()
        }
        binding.tonesDecrementButton.setOnClickListener {
            toneModifiable.get()?.removeNote()
        }
        binding.beatButtons.forEachIndexed { index, view ->
            (view as? ToneButtonView)?.button?.setOnClickListener {
                toneModifiable.get()?.rotateNote(index)
            }
        }
    }

    override fun bind(modifiable: ToneModifiable) {
        this.toneModifiable = WeakReference(modifiable)
    }

    override fun unbind() {
        this.toneModifiable.clear()
    }

    override fun updateView(beat: Beat) {
        binding.tonesTextView.text = context.getString(R.string.tones_count, beat.noteCount.toInt())
        binding.tonesIncrementButton.isEnabled = beat.canAddNote
        binding.tonesDecrementButton.isEnabled = beat.canRemoveNote

        val beatButtons = binding.beatButtons
        val currentNumberOfButtons = beatButtons.childCount

        val tones = beat.makeNotes()

        for (i in 0 until currentNumberOfButtons) {
            val button = beatButtons.getChildAt(i)
            if (i < tones.size) {
                button.visibility = View.VISIBLE
                (button as? ToneButtonView)?.updateButtonImage(tones[i])
            } else {
                button.visibility = View.GONE
            }
        }
    }

    fun toggleControls(controlsEnabled: Boolean) {
        binding.tonesIncrementButton.isEnabled = controlsEnabled
        binding.tonesDecrementButton.isEnabled = controlsEnabled
    }

    fun highlightToneButton(index: Int) {
        uiScope.launch {
            binding.beatButtons.children.forEachIndexed { viewIndex, view ->
                if (viewIndex == index) {
                    (view as? ToneButtonView)?.highlightButton()
                } else {
                    (view as? ToneButtonView)?.unhighlightButton()
                }
            }
        }
    }

    fun unhighlightToneButton() {
        uiScope.launch {
            binding.beatButtons.children.forEach { child ->
                (child as? ToneButtonView)?.unhighlightButton()
            }
        }
    }

}