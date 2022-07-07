package htw.gma_sose22.metronomeui.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import htw.gma_sose22.R
import htw.gma_sose22.databinding.FragmentLibraryBinding
import htw.gma_sose22.metronomekit.MetronomeProApp
import htw.gma_sose22.metronomekit.beat.BeatPattern
import htw.gma_sose22.metronomepro.EditorActivity
import htw.gma_sose22.metronomeui.util.ListAdapterItemClickListener
import htw.gma_sose22.metronomeui.util.SwipeToDeleteCallback
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class LibraryFragment : Fragment(), ListAdapterItemClickListener<BeatPattern> {

    // region Properties

    private var _binding: FragmentLibraryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var viewModel: LibraryViewModel? = null

    // endregion
    // region Overridden Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = LibraryViewModel((activity?.application as MetronomeProApp).repository)
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        context?.let {
            configureRecyclerView(it)
        }

        binding.fab.setOnClickListener {
            launchEditorActivity(null)
        }

        return binding.root
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, intentData)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        Log.d("LibraryFragment", "Got Intent result")

        if (resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(EditorActivity.intentJSONKey)?.let { jsonString ->
                val pattern: BeatPattern = Json.decodeFromString(jsonString)
                viewModel?.insertPattern(pattern)
            }
        } else {
            Toast.makeText(
                context,
                "Pattern was not saved",
                Toast.LENGTH_LONG
            ).show()
        }

        super.onActivityResult(requestCode, resultCode, intentData)
    }

    // endregion
    // region Configuration

    private fun configureRecyclerView(context: Context) {
        val adapter = LibraryAdapter(context, this)
        binding.recyclerView.adapter = adapter

        enableSwipeToDeleteAndUndo(context)

        viewModel?.allPatterns?.observe(viewLifecycleOwner) { allPatterns ->
            adapter.submitList(allPatterns)
        }
    }

    private fun enableSwipeToDeleteAndUndo(context: Context) {
        val callback = SwipeToDeleteCallback(context) { position ->
            val pattern = viewModel?.allPatterns?.value?.get(position)
            viewModel?.removePattern(position)
            val snackbar = Snackbar
                .make(
                    binding.recyclerView,
                    context.getString(R.string.pattern_deleted),
                    Snackbar.LENGTH_LONG
                )
            snackbar.setAction(R.string.undo) {
                viewModel?.restorePattern(pattern, position)
                binding.recyclerView.scrollToPosition(position)
            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    // endregion
    // region Helpers

    override fun handleClick(item: BeatPattern, adapterPosition: Int) {
        launchEditorActivity(item)
    }

    private fun launchEditorActivity(existingPattern: BeatPattern?) {
        val intent = Intent(context, EditorActivity::class.java)
        existingPattern?.let {
            val jsonString = Json.encodeToString(it)
            intent.putExtra(EditorActivity.intentJSONKey, jsonString)
        }
        activity?.startActivityFromFragment(this, intent, 1)
    }

    // endregion

}