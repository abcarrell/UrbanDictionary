package com.acarrell.urbandictionary.events

import com.acarrell.urbandictionary.adapter.DictionaryAdapter

class SortEvent(val sortKey: DictionaryAdapter.SortKey) : Event()