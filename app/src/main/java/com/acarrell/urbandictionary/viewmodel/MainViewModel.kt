package com.acarrell.urbandictionary.viewmodel

import androidx.lifecycle.ViewModel
import com.acarrell.urbandictionary.R
import com.acarrell.urbandictionary.adapter.DictionaryAdapter
import com.acarrell.urbandictionary.events.Event
import com.acarrell.urbandictionary.events.SortEvent
import com.acarrell.urbandictionary.events.ToastEvent
import com.acarrell.urbandictionary.events.UpdateEvent
import com.acarrell.urbandictionary.model.DictionaryEntry
import com.acarrell.urbandictionary.service.ServiceController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val eventBus = PublishRelay.create<Event>()

    var searchText: String? = null
    private var sortKey: DictionaryAdapter.SortKey = DictionaryAdapter.SortKey.ThumbsUp
    var showProgress = false

    val data: MutableList<DictionaryEntry> = ArrayList()
    val adapter = DictionaryAdapter(data)

    var serviceController: ServiceController? = null

    fun search() {
        searchText?.let { searchTerm ->
            serviceController?.let { api ->
                showProgress = true
                api.getDefinitions(searchTerm)
                .subscribe({ entries ->
                    updateData(entries)
                    showProgress = false
                }, {
                    eventBus.accept(ToastEvent(it.message))
                    showProgress = false
                })
            }
        }
    }

    fun sortEntries() {
        sortKey.let {
            adapter.sortData(it)
            adapter.notifyDataSetChanged()
            sortKey = when (it) {
                is DictionaryAdapter.SortKey.ThumbsUp -> {
                    DictionaryAdapter.SortKey.ThumbsDown
                }
                is DictionaryAdapter.SortKey.ThumbsDown -> {
                    DictionaryAdapter.SortKey.ThumbsUp
                }
            }
            eventBus.accept(SortEvent(sortKey))
        }
    }

    private fun updateData(entries: List<DictionaryEntry>) {
        data.clear()
        data.addAll(entries)
        sortEntries()
        adapter.notifyDataSetChanged()
        eventBus.accept(UpdateEvent())
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun addAllDisposables(vararg disposable: Disposable) {
        compositeDisposable.addAll(*disposable)
    }

    fun clearDisposables() {
        compositeDisposable.clear()
    }

    fun destroy() {
        clearDisposables()
    }
}
