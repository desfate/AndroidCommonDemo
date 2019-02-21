package com.defate.mac.androidcommondemo.samples

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.defate.mac.androidcommondemo.R

import kotlinx.android.synthetic.main.activity_scp.*

/**
 * 这是一个模仿sunflower的scp实例
 *
 * 采用单activity + Navigation(多fragment) 的模式
 *
 */
class ScpActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_scp)
        val navController = Navigation.findNavController(this, R.id.fragment_scp)
    }
}