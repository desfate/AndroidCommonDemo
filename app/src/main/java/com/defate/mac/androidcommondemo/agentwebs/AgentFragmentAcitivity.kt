package com.defate.mac.androidcommondemo.agentwebs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.defate.mac.androidcommondemo.R
import com.defate.mac.androidcommondemo.agentwebs.sonic.SonicJavaScriptInterface.PARAM_CLICK_TIME

class AgentFragmentAcitivity: AppCompatActivity(){

    lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentweb_fragment)
        mFragmentManager = supportFragmentManager
        val ft: FragmentTransaction = mFragmentManager.beginTransaction()
        var mBundle = Bundle()
        mBundle.putLong(PARAM_CLICK_TIME, intent.getLongExtra(PARAM_CLICK_TIME, -1L))
        mBundle.putString("url_key", "http://mc.vip.qq.com/demo/indexv3")

        ft.add(
            R.id.container_framelayout,
            VasSonicFragment.create(mBundle),
            AgentWebFragment::class.java.name!!
        )

        ft.commit()
    }
}