package com.acarrell.urbandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.acarrell.urbandictionary.R
import com.acarrell.urbandictionary.databinding.DictionaryEntryBinding
import com.acarrell.urbandictionary.model.DictionaryEntry
import com.acarrell.urbandictionary.viewmodel.EntryViewModel
import com.acarrell.urbandictionary.viewmodel.MainViewModel

class DictionaryAdapter(private val data: MutableList<DictionaryEntry>) : RecyclerView.Adapter<DictionaryAdapter.EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dictionary_entry, parent, false)
        return EntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = data[position]
        holder.viewModel = EntryViewModel(entry)
        holder.bind()
    }

    override fun getItemCount(): Int = data.size

    fun sortData(sortKey: SortKey) {
        when (sortKey) {
            is SortKey.ThumbsUp -> {
                data.sortByDescending { entry -> entry.thumbsUp }
            }
            is SortKey.ThumbsDown -> {
                data.sortByDescending { entry -> entry.thumbsDown }
            }
        }
    }

    inner class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: DictionaryEntryBinding? = DataBindingUtil.bind(view)
        var viewModel: EntryViewModel? = null

        fun bind() {
            binding?.viewModel = viewModel
        }

        fun unbind() {
            binding?.viewModel = null
        }
    }

    sealed class SortKey(
        @DrawableRes val iconRes: Int
    ) {
        object ThumbsUp : SortKey(R.drawable.ic_thumbs_up_btn)
        object ThumbsDown : SortKey(R.drawable.ic_thumbs_down_btn)
    }
}