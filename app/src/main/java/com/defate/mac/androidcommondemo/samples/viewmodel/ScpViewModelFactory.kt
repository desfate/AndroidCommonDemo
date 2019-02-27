package com.defate.mac.androidcommondemo.samples.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.defate.mac.androidcommondemo.samples.repository.ScpRepository

class ScpViewModelFactory (private val scpRepository: ScpRepository)
    : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScpViewModel(scpRepository) as T
    }
}