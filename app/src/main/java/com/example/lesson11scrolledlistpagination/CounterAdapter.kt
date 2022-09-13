package com.example.lesson11scrolledlistpagination

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson11scrolledlistpagination.databinding.ItemCounterBinding

sealed class Item {
    data class Counter(val value: Int) : Item()
}

class CounterAdapter(
    context: Context
) : ListAdapter<Item, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CounterViewHolder(
            binding = ItemCounterBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CounterViewHolder).bind(getItem(position) as Item.Counter)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}

fun load(lastCounter: Int, itemsToLoad: Int): List<Int> {
    return List(itemsToLoad) {
        lastCounter + 1 + it
    }
}

class CounterViewHolder(
    private val binding: ItemCounterBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item.Counter) {
        binding.textView.text = item.value.toString()
    }
}