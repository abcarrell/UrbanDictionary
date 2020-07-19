package com.acarrell.urbandictionary.viewmodel

import android.view.View
import com.acarrell.urbandictionary.model.DictionaryEntry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class EntryViewModelTest {
    private lateinit var viewModel: EntryViewModel

    @Before
    fun setUp() {
        val entry = DictionaryEntry(
            defId = DEFINITION_ID,
            word = WORD,
            definition = DEFINITION,
            example = EXAMPLE,
            thumbsUp = THUMBS_UP,
            thumbsDown = THUMBS_DOWN,
            permalink = PERMALINK,
            author = AUTHOR,
            writtenOn = WRITTEN_ON,
            soundUrls = SOUND_URLS
        )
        viewModel = EntryViewModel(entry)
    }

    @Test
    fun `viewModel SHOULD return all applicable fields`() {
        assertEquals(viewModel.definition.get(), DEFINITION)
        assertEquals(viewModel.word.get(), WORD)
        assertEquals(viewModel.example.get(), EXAMPLE)
        assertEquals(viewModel.thumbsUp.get(), THUMBS_UP.toString())
        assertEquals(viewModel.thumbsDown.get(), THUMBS_DOWN.toString())
    }

    @Test
    fun `handleItemClick SHOULD swap the visibility for the extended portion of the list item`() {
        viewModel.itemVisibility.set(View.GONE)

        viewModel.handleItemClick()
        assertEquals(viewModel.itemVisibility.get(), View.VISIBLE)

        viewModel.handleItemClick()
        assertEquals(viewModel.itemVisibility.get(), View.GONE)
    }

    private companion object {
        const val DEFINITION_ID = 3322419
        const val WORD = "wat"
        const val DEFINITION = "The only [proper] [response] to something that makes absolutely [no sense]."
        const val EXAMPLE = "1: If all the animals on the [equator] were capable of [flattery], Halloween and Easter would fall on the same day. 2: wat 1: Wow your cock is almost as big as my dad's. 2: wat 1: I accidentially a whole [coke bottle] 2: You accidentially what? 1: A whole coke bottle 2: wat"
        const val THUMBS_UP = 3716
        const val THUMBS_DOWN = 427
        const val PERMALINK = "http://wat.urbanup.com/3322419"
        const val AUTHOR = "watwat"
        val WRITTEN_ON = Date(1220486400000)
        val SOUND_URLS = emptyList<String>()
    }
}