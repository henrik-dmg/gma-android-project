package htw.gma_sose22.metronomeui.editor

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import htw.gma_sose22.metronomekit.beat.Beat
import htw.gma_sose22.metronomepro.databinding.FragmentEditorBinding

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

        val root: View = binding.root
        val editorAdapter = EditorAdapter { flower -> adapterOnClick(flower) }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = editorAdapter
        viewModel?.beatsLiveData?.observe(viewLifecycleOwner) {
            it?.let {
                editorAdapter.submitList(it as MutableList<Beat>)
            }
        }

        val fab: View = binding.fab
        fab.setOnClickListener {
            viewModel?.addNewBeat()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(beat: Beat) {
        //val intent = Intent(this, FlowerDetailActivity()::class.java)
        //intent.putExtra(FLOWER_ID, flower.id)
        //startActivity(intent)
        Log.d("EditorFragment", "Beat was tapped $beat")
    }

}