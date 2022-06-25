package htw.gma_sose22.metronomeui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import htw.gma_sose22.R

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
        Log.d("BeatView", "BeatView was init")
    }

}