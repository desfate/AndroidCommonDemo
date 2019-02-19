package com.defate.mac.androidcommondemo.kotilins.extends_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View

/**
 * kotlin 实现自定义view
 */
class KotlinMyViews @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View( context, attrs, defStyleAttr){

    private val mContext: Context = context
    init {

    }

    private val drawable: ShapeDrawable = kotlin.run {
        val x = 10
        val y = 10
        val width = 300
        val height = 300

        ShapeDrawable(OvalShape()).apply {
            // If the color isn't set, the shape uses black as the default.
            paint.color = 0xff74AC23.toInt()
            // If the bounds aren't set, the shape can't be drawn.
            setBounds(x, y, x + width, y + height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawable.draw(canvas)
    }
}