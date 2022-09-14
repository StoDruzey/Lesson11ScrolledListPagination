package com.example.lesson11scrolledlistpagination

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson11scrolledlistpagination.databinding.ItemCounterBinding
import com.example.lesson11scrolledlistpagination.databinding.ItemLoadingBinding

sealed class Item {
    data class Counter(val value: Int) : Item()
//we need create a type "loading". Since it has only one state (on or off - because it is single for the whole list),
//it is sufficient to create singleton:
    object Loading : Item()
}

class CounterAdapter(
    context: Context
) : ListAdapter<Item, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    //if our adapter consists more than 1 type, we will override the next method:
    override fun getItemViewType(position: Int): Int { //we check the type of the element and return a specific element index
        return when (getItem(position)) {
            is Item.Counter -> TYPE_COUNTER
            is Item.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COUNTER -> {
                CounterViewHolder(
                    binding = ItemCounterBinding.inflate(layoutInflater, parent, false))
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false))
            }
            else -> {
                error("Unsupported viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CounterViewHolder).bind(getItem(position) as Item.Counter)
    }

    companion object {
//create constants for getItemViewType()
        private const val TYPE_COUNTER = 0
        private const val TYPE_LOADING = 1

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

//create a new class for loading element
class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)