package com.defate.mac.androidcommondemo

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.defate.mac.common_android.notification.NotificationServices
import com.defate.mac.common_android.notification.data.MessagingStyleCommsAppData
import com.defate.mac.common_android.screen.ScreenTools

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var key: Boolean = true

//    private lateinit var customDrawableView: CustodrawableView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        customDrawableView = CustodrawableView(this)

//        setContentView(customDrawableView)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var bigTextStyleReminderAppData: MessagingStyleCommsAppData = MessagingStyleCommsAppData.getInstance(this)
        bigTextStyleReminderAppData.setmClazz(MainActivity::class.java)

        NotificationServices.getMessagingStyleNotification(this, bigTextStyleReminderAppData)

        var key: Boolean = true

        main_text.setOnClickListener {
            if (key) {
                key = false
                ScreenTools.toggleHideyBar(this)
            } else {
                key = true
                ScreenTools.showSystemUI(this)
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (Build.VERSION.SDK_INT < 16) { //在Android 4.0和更低版本上隐藏状态栏
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else { //在Android 4.1及更高版本上隐藏状态栏
            // Hide the status bar.
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            actionBar?.hide()
        }

        //您可以使用SYSTEM_UI_FLAG_HIDE_NAVIGATION标志隐藏导航栏 。此代码段隐藏了导航栏和状态栏：
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

//        this.window?.decorView?.apply { systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> ScreenTools.toggleHideyBar(this)
        }
        return true
    }
}
