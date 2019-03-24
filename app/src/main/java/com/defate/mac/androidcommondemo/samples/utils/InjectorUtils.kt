package com.defate.mac.androidcommondemo.samples.utils

import android.content.Context
import com.defate.mac.androidcommondemo.samples.repository.ScpRepository
import com.defate.mac.androidcommondemo.samples.room.AppDatabase
import com.defate.mac.androidcommondemo.samples.viewmodel.ScpListViewModelFactory
import com.defate.mac.androidcommondemo.samples.viewmodel.ScpViewModelFactory

object InjectorUtils{

    private fun getScpRepository(context: Context
    ): ScpRepository{
        return ScpRepository.getInstance(AppDatabase.getInstance(context).ScpDao())
    }

    /**
     * 获取Scp list ViewModel
     */
    fun provideScpListViewModelFactory(context: Context
    ): ScpListViewModelFactory{
        return ScpListViewModelFactory(getScpRepository(context))
    }

    /**
     * 获取Scp detail ViewModel
     */
    fun provideScpDetailViewModelFactory(context: Context
    ): ScpViewModelFactory{
        return ScpViewModelFactory(getScpRepository(context))
    }
}