package com.defate.mac.androidcommondemo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setSupportActionBar(my_toolbar)
    }

}