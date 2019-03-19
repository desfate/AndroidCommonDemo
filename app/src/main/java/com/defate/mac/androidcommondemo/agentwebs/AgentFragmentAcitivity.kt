package com.defate.mac.androidcommondemo.agentwebs

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R

class AgentFragmentAcitivity: AppCompatActivity(){

    lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentweb_fragment)
        mFragmentManager = supportFragmentManager
        val ft: FragmentTransaction = mFragmentManager.beginTransaction()
        ft.add(
            R.id.container_framelayout,
            AgentWebFragment()
        )
        ft.commit()
    }
}