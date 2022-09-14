package com.example.lesson11scrolledlistpagination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson11scrolledlistpagination.databinding.FragmentFirstBinding

class FragmentFirst : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        CounterAdapter(requireContext())
    }

    private var lastCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = linearLayoutManager //recyclerView from xml
            recyclerView.adapter = adapter
            recyclerView.addVerticalSpace() //this extension is defined in the file RecyclerViewExtensions
            recyclerView.addPaginationListener(linearLayoutManager, ITEMS_TO_LOAD) {
                loadData()
            }
            loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        val items = load(lastCounter, ITEMS_TO_LOAD) //list from adapter
        lastCounter = items.last() //after loading data change the value of the counter
        val recentItems = adapter.currentList //recent list from adapter to add a new list to scroll up and down:
        val newItems = recentItems + items.map { Item.Counter(it) }
        adapter.submitList(newItems)
    }

    companion object {
        private const val PAGE_SIZE = 50
        private const val ITEMS_TO_LOAD = 15
    }
}