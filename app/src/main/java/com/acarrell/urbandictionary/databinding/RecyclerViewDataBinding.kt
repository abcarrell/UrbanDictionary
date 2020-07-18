package com.acarrell.urbandictionary.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.acarrell.urbandictionary.adapter.DictionaryAdapter
import com.acarrell.urbandictionary.model.DictionaryEntry

class RecyclerViewDataBinding {
    @BindingAdapter("app:adapter", "app:data")
    fun bind(recyclerView: RecyclerView, adapter: DictionaryAdapter, data: List<DictionaryEntry>) {
        recyclerView.adapter = adapter
    }
}