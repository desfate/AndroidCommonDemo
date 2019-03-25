package com.defate.mac.androidcommondemo.samples.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.defate.mac.androidcommondemo.samples.repository.ScpRepository
import com.defate.mac.androidcommondemo.samples.room.Scp

class ScpListViewModel(private val scpRepository: ScpRepository): ViewModel(){

    private var scpList = MutableLiveData<List<Scp>>()

    fun getAllScp(): MutableLiveData<List<Scp>>{
        return scpList.apply { scpRepository.getScp() }
    }


}
