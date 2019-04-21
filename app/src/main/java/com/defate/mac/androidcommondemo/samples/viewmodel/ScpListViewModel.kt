package com.defate.mac.androidcommondemo.samples.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.defate.mac.androidcommondemo.samples.repository.ScpRepository
import com.defate.mac.androidcommondemo.samples.room.Scp

class ScpListViewModel(private val scpRepository: ScpRepository): ViewModel(){

    private var scpList = MutableLiveData<List<Scp>>()

    fun getAllScp(): MutableLiveData<List<Scp>>{
        return scpList.apply { scpRepository.getScp() }
    }


}
