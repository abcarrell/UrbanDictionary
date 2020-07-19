package com.acarrell.urbandictionary.application

import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.acarrell.urbandictionary.databinding.AppDataBindingComponent
import com.acarrell.urbandictionary.service.ServiceController

class UDApplication : Application() {
    val serviceController by lazy {
        ServiceController.create()
    }

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())
    }

    companion object {
        @JvmStatic
        fun get(context: Context): UDApplication {
            return context.applicationContext as UDApplication
        }
    }
}