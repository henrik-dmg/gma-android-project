package htw.gma_sose22.metronomepro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import htw.gma_sose22.R
import htw.gma_sose22.databinding.ActivityEditorBinding
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomekit.beat.ToneChangeHandler
import htw.gma_sose22.metronomekit.metronome.MetronomeService
import htw.gma_sose22.metronomeui.editor.EditorAdapter
import htw.gma_sose22.metronomeui.editor.EditorViewHolder
import htw.gma_sose22.metronomeui.editor.EditorViewModel
import htw.gma_sose22.metronomeui.editor.EditorViewModelFactory
import htw.gma_sose22.metronomeui.util.SwipeToDeleteCallback
import htw.gma_sose22.metronomeui.util.inputText
import htw.gma_sose22.metronomeui.util.showInput
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EditorActivity : AppCompatActivity(), ToneChangeHandler {

    companion object {
        const val intentJSONKey = "INTENT_PATTERN_JSON_KEY"
    }

    private var beatPattern: BeatPattern? = null
    private val viewModel: EditorViewModel by viewModels {
        EditorViewModelFactory(beatPattern)
    }

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(intentJSONKey)?.let {
            beatPattern = Json.decodeFromString(it)
        }

        val binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setSupportActionBar(binding.topAppBar)

        recyclerView = binding.recyclerView

        val adapter = EditorAdapter(applicationContext)
        recyclerView?.adapter = adapter

        enableSwipeToDeleteAndUndo(binding, applicationContext)

        viewModel.beats.observe(this) {
            it?.let {
                adapter.submitList(it as MutableList)
            }
        }

        viewModel.isPlaying.observe(this) { isPlaying ->
            binding.fab2.setImageResource(
                if (isPlaying) R.drawable.round_stop_24
                else R.drawable.round_play_arrow_24
            )
            supportActionBar?.setDisplayHomeAsUpEnabled(!isPlaying)
        }

        binding.fab.setOnClickListener {
            viewModel.addBeat()
        }
        binding.fab2.setOnClickListener {
            MetronomeService.configureToneChangeHandler(this)
            viewModel.handlePlaybackButtonTapped()
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save_pattern -> {
                    handleSaveButtonClicked()
                    true
                }
                R.id.delete_pattern -> {
                    handleDeleteButtonClicked()
                    true
                }
                else -> false
            }
        }
    }

    private fun handleSaveButtonClicked() {
        prompForPatternName(viewModel.beatPattern?.patternName)
    }

    private fun handleDeleteButtonClicked() {
        viewModel.removeAllBeats()
    }

    private fun enableSwipeToDeleteAndUndo(binding: ActivityEditorBinding, context: Context) {
        val callback = SwipeToDeleteCallback(context) { position ->
            val beat = viewModel.beats.value?.get(position)
            viewModel.removeBeat(position)
            val snackbar = Snackbar
                .make(
                    binding.recyclerView,
                    context.getString(R.string.beat_deleted),
                    Snackbar.LENGTH_LONG
                )
            snackbar.setAction(R.string.undo) {
                viewModel.restoreBeat(beat, position)
                binding.recyclerView.scrollToPosition(position)
            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun prompForPatternName(existingName: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.beat_pattern_name_prompt_title)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                callbackMainActivity(dialog.inputText(tilId = R.id.dialog_text_input_layout))
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .showInput(
                layout = R.layout.editor_prompttextfield,
                tilId = R.id.dialog_text_input_layout,
                hintRes = R.string.beat_pattern_name_placeholder,
                counterMaxLength = 15,
                prefilled = existingName ?: ""
            )
    }

    private fun callbackMainActivity(patternName: String) {
        viewModel.makePattern(patternName)?.let { pattern ->
            Log.d("EditorActivity", "Calling back with patternName $patternName")
            // Encoding JSON here is absolutely terrible and unsafe,
            // but it's the best I could come up with for now
            val jsonString = Json.encodeToString(pattern)
            val intent = Intent()
            intent.putExtra(intentJSONKey, jsonString)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    override fun currentToneChanged(toneIndex: Int, beatIndex: Int) {
        (recyclerView?.findViewHolderForAdapterPosition(beatIndex) as? EditorViewHolder)?.highlightNote(toneIndex)
    }

    override fun playbackStopped() {
        // TODO: Unhighlight last view
        viewModel.playbackStopped()
    }

}