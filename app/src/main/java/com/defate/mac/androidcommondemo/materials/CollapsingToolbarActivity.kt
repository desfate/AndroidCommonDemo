package com.defate.mac.androidcommondemo.materials
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.navigation.Navigation
import com.defate.mac.androidcommondemo.R
import com.defate.mac.androidcommondemo.samples.adapter.MListAdapter
import com.defate.mac.androidcommondemo.samples.adapter.MViewHolder
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

/**
 * //https://www.jianshu.com/p/94ceeb8bbf87 文档来自
 * 这里用到 CoordinatorLayout  ：官方介绍 CoordinatorLayout is a super-powered FrameLayout
 *
 * 同时用到 CollapsingToolbarLayout  ：官方介绍 CollapsingToolbarLayout is a wrapper for 《Toolbar》 which implements a collapsing app bar. It is designed to be used as a direct child of a AppBarLayout.
 *
 * 方法及功能
 * 折叠标题  标题外观可以通过collapsedTextAppearance和expandedTextAppearance调整
 * 内容蒙纱  setContentScrim(Drawable)
 * 状态栏蒙纱  setStatusBarScrim(Drawable)
 * 视差滚动   子视图可以选择以视差方式在此布局内滚动
 * 固定位置的view 子View可以选择全局固定在空间
 *
 *
 * layout_scrollFlags有5种动作，分别是：
 * scroll
 * enterAlways  子View 添加layout_scrollFlags属性 的值有enterAlways 时, 当ScrollView 向下滑动时，子View 将直接向下滑动，而不管ScrollView 是否在滑动。注意：要与scroll 搭配使用，否者是不能滑动的
 * enterAlwaysCollapsed
 * exitUntilCollapsed
 * snap
 *
 * addOnOffsetChangedListener当AppbarLayout 的偏移发生改变的时候回调
 * getTotalScrollRange返回AppbarLayout 所有子View的滑动范围
 * removeOnOffsetChangedListener移除监听器
 * setExpanded (boolean expanded,  boolean animate)设置AppbarLayout 是展开状态还是折叠状态，animate 参数控制切换到新的状态时是否需要动画
 * setExpanded (boolean expanded)设置AppbarLayout 是展开状态还是折叠状态,默认有动画
 *
 */
/**
 * Collapsingtoolbar + AppBarLayout + CardView + Palette
 */
class CollapsingToolbarActivity : AppCompatActivity(){
    var color: Int = 0  //根据图片生成的主题色
    var titleColors: Int = 0 //对比色
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.title = "Scp-001"
//
//        recyclerList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        recyclerList.adapter = MListAdapter<String>(
//            items = listOf("123","453","345","345","345","345","345","345","345","345","345"),
//            layoutCallBack = {
//                R.layout.items_normal_tv },
//            convertCallBack = { mViewHolder: MViewHolder, s: String, i: Int ->
//                mViewHolder.getView<TextView>(R.id.item_tv)?.setText(s + "position = i")
//                mViewHolder.getView<TextView>(R.id.item_tv_content)?.setText("aaaaa")
//                mViewHolder.getView<Button>(R.id.item_btn)?.setText(s)
//                mViewHolder.getView<Button>(R.id.item_btn)?.setOnClickListener {
//                    Navigation.findNavController(it).popBackStack()
//                }
//            })
//
//
//
//        Palette.from(BitmapFactory.decodeResource(resources,R.mipmap.scp)).generate { palette ->
//            val swatch: Palette.Swatch  = palette.mutedSwatch!!  //获取图片的主题色值
//            color = swatch.rgb
//            titleColors = palette.getLightMutedColor(Color.WHITE)
//            toolbar.setBackgroundColor(color)
//        }
//
//        /**
//         * 滑动时的回调
//         */
//        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
//            //verticalOffset始终为0以下的负数
//            val percent = Math.abs(p1 * 1.0f) / p0!!.totalScrollRange
//            toolbar.setBackgroundColor(changeAlpha(color, percent))
//        })
    }


    /** 根据百分比改变颜色透明度  */
    fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }


}