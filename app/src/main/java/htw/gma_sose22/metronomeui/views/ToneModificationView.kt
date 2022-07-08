package htw.gma_sose22.metronomeui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.TonesViewBinding
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomekit.beat.Tone
import java.lang.ref.WeakReference

class ToneModificationView : ConstraintLayout, BeatModificationView<ToneModifiable> {

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
        for (i in 0 until binding.beatButtons.childCount) {
            val button = binding.beatButtons.getChildAt(i) as MaterialButton
            button.setOnClickListener {
                toneModifiable.get()?.rotateNote(i)
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
                updateButtonImage(button as MaterialButton, tones[i])
            } else {
                button.visibility = View.GONE
            }
        }
    }

    fun toggleControls(controlsEnabled: Boolean) {
        binding.tonesIncrementButton.isEnabled = controlsEnabled
        binding.tonesDecrementButton.isEnabled = controlsEnabled
    }

    // MARK: - Helpers

    private fun updateButtonImage(button: MaterialButton, tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = context.resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_default)
        }
    }

}