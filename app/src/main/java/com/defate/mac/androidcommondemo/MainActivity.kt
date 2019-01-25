package com.defate.mac.androidcommondemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.defate.mac.common_android.NotificationTools

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val events = arrayOf("123","32","sdfsg")

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle("Event tracker details:")
        inboxStyle.setSummaryText("test"); //底部栏文本
        for (i in 0 until events.size) {
            inboxStyle.addLine(events[i])

        }

        NotificationTools.showNotication(this,
            NotificationTools.getNotificationCompatBuilder(this).setContentTitle("xxx") //设置通知栏标题
            .setContentText("xxx") //设置通知栏显示内容
            .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知优先级  //五个优先级  从-2到2
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true) //设置这个标志当用户单击面板就可以将通知取消)
            .setStyle(inboxStyle))



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
