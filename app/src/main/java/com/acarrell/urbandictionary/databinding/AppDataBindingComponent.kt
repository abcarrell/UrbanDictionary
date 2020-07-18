package com.acarrell.urbandictionary.databinding

import androidx.databinding.DataBindingComponent

class AppDataBindingComponent : DataBindingComponent {
    override fun getRecyclerViewDataBinding(): RecyclerViewDataBinding {
        return RecyclerViewDataBinding()
    }
}