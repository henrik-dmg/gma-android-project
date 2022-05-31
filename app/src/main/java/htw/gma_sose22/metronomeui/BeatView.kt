package htw.gma_sose22.metronomeui

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import htw.gma_sose22.metronomepro.R

class BeatView(context: Context): LinearLayout(context) {

    /**
     * Load component XML layout
     */
    private fun initControl(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.beat_view, this)
    }

    init {
        initControl(context)
    }

}