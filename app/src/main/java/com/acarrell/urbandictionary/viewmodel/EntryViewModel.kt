package com.acarrell.urbandictionary.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.acarrell.urbandictionary.model.DictionaryEntry

class EntryViewModel(private val entry: DictionaryEntry) {
    val word = ObservableField<String>(entry.word)
    val definition = ObservableField<String>(entry.definition)
    val thumbsUp = ObservableField<String>(entry.thumbsUp.toString())
    val thumbsDown = ObservableField<String>(entry.thumbsDown.toString())
    val example = ObservableField<String>(entry.example)
    val itemVisibility = ObservableInt(View.GONE)
    val moreText = ObservableField<String>("More...")

    fun handleItemClick() {
        if (itemVisibility.get() == View.GONE) {
            itemVisibility.set(View.VISIBLE)
            moreText.set("Less...")
        } else {
            itemVisibility.set(View.GONE)
            moreText.set("More...")
        }
    }
}