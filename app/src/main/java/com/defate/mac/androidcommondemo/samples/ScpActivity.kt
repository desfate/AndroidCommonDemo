package com.defate.mac.androidcommondemo.samples

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R

import kotlinx.android.synthetic.main.activity_scp.*

/**
 * 这是一个模仿sunflower的scp实例
 *
 * 采用单activity + Navigation(多fragment) 的模式
 *
 */
class ScpActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scp)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标 。
    }

}