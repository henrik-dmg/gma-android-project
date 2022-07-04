package htw.gma_sose22.metronomeui.editor

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import htw.gma_sose22.R
import htw.gma_sose22.databinding.FragmentEditorBinding

class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var viewModel: EditorViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EditorViewModel::class.java]
        _binding = FragmentEditorBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView

        context?.let { context ->
            val adapter = EditorAdapter(context)
            recyclerView.adapter = adapter

            viewModel?.beats?.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitList(it as MutableList)
                }
            }
        }

        //val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(editorAdapter))
        //itemTouchHelper.attachToRecyclerView(recyclerView)

        val fab = binding.fab
        fab.setOnClickListener {
            viewModel?.addBeat()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.editor_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_pattern -> {
                viewModel?.removeAllBeats()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}