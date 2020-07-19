package com.acarrell.urbandictionary.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acarrell.urbandictionary.viewmodel.MainViewModel

class MainViewModelFactory(private val application: UDApplication) : ViewModelProvider.Factory {
    private val serviceController = application.serviceController

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModel::class.java -> MainViewModel(application, serviceController)
            else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
        } as T
}