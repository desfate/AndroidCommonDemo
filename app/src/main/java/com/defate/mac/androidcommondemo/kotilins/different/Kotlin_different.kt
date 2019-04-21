package com.defate.mac.androidcommondemo.kotilins.different

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.defate.mac.androidcommondemo.R

class Kotlin_different{
    /**
     * kotlin获取drawable的写法
     */
    fun getDrawable(context : Context) {
        val myImage: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_alarm_white_48dp, null)
    }
}