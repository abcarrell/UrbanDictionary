package com.acarrell.urbandictionary.application

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.acarrell.urbandictionary.databinding.AppDataBindingComponent
import com.acarrell.urbandictionary.service.ServiceController

class UDApplication : Application() {
    private var serviceController: ServiceController? = null

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())
    }

    fun getServiceController(): ServiceController = serviceController
        ?: run {
            serviceController = ServiceController.create()
            return@run serviceController!!
        }
}