package com.defate.mac.androidcommondemo.agentwebs

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.activity_agentweb.*


class AgentWebActivity: AppCompatActivity(){

    val url: String = "https://www.baidu.com"

    lateinit var agentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentweb)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar?.setNavigationOnClickListener{
            showDialog()
        }
        toolbar?.title = ""

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(container, LinearLayout.LayoutParams(-1,-1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)  //设置ChromeClient
            .setWebViewClient(mWebViewClient)      //设置WebViewClient
            .setMainFrameErrorView(R.layout.agentview_error, -1)   //设置错误展示页面
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)  //打开其他应用的时候 是否弹窗提示
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)
    }


    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //do you work
    }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            toolbar_title?.setText(title)
        }
    }

    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            //do your work
        }
    }


    lateinit var mAlertDialog: AlertDialog
    private fun showDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = AlertDialog.Builder(this)
                .setMessage("您确定要关闭该页面吗?")
                .setNegativeButton("再逛逛") { dialog, which ->
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss()
                    }
                }//
                .setPositiveButton("确定") { dialog, which ->
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss()
                    }
                    this@AgentWebActivity.finish()
                }.create()
        }
        mAlertDialog.show()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(agentWeb?.handleKeyEvent(keyCode, event)) return true
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        agentWeb?.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb?.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle.onDestroy()
        super.onDestroy()
    }



}