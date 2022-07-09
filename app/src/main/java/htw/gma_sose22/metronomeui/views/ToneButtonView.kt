package htw.gma_sose22.metronomeui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import htw.gma_sose22.R
import htw.gma_sose22.databinding.ToneButtonViewBinding
import htw.gma_sose22.metronomekit.beat.Tone


class ToneButtonView : ConstraintLayout {


    private val binding: ToneButtonViewBinding

    val button: MaterialButton
        get() = binding.button

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ToneButtonViewBinding.inflate(inflater, this)
    }

    fun highlightButton(shouldHighlight: Boolean) {
        binding.highlightingPip.visibility = if (shouldHighlight) View.VISIBLE else View.INVISIBLE
    }

    fun updateButtonImage(tone: Tone) {
        when (tone) {
            Tone.emphasised -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_emphasised)
            Tone.muted -> button.icon = context.resources.getDrawable(R.drawable.ic_note_muted)
            Tone.regular -> button.icon =
                context.resources.getDrawable(R.drawable.ic_note_default)
        }
    }

}