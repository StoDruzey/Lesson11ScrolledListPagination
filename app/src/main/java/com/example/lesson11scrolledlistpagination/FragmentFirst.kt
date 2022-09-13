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
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
            val items = load(0, 50)
            adapter.submitList(items.map { Item.Counter(it) })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}