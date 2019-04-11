package com.defate.mac.androidcommondemo.materials

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.defate.mac.androidcommondemo.R
import kotlinx.android.synthetic.main.activity_normal_views.*

/**
 * 一些常用的控件尝试
 * TextInputLayout  设计要求：
 * 输入框点击区域高度至少48dp，但横线并不在点击区域底部，还有8dp距离。
 * 这里写图片描述
 * 右下角可以加入字数统计。字数统计不要默认显示，字数接近上限时在显示出来。
 * 同时有多个错误提示时，屏幕顶部应有一个全局的错误提示通知。
 * 输入框尽量带有自动补全功能。
 *
 * SnackBar
 * SnackBar至多包含一个操作项，不能包含图标，不能出现一个以上的SnackBar。
 *
 */
class NormalViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_views)
//        text_Input_layout.error = "字数超过最大限制"

        m_test_swCpt.error = "错误" // switch为啥要有错误 ？？？  这个sitch btn有点问题的  ==。 回头再来看
        m_test_swCpt.textOff = "off the choice"
        m_test_swCpt.textOn = "on the choice"
        m_test_swCpt.showText = true
        m_test_swCpt.switchPadding = 100
        m_test_swCpt.isChecked = true
        m_test_swCpt.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) showNormalSnackbar()
                else showBtnSnackbar()
            }
        }

        val view: View = LayoutInflater.from(this)
            .inflate(
                R.layout.manu_circle_icon,
                mbottom_navigation_view.getChildAt(0) as BottomNavigationMenuView,
                false
            )

        ((mbottom_navigation_view.getChildAt(0) as BottomNavigationMenuView).getChildAt(3) as BottomNavigationItemView).addView(view)
        view.findViewById<TextView>(R.id.tv_msg_count).text = "12"

        BottomNavigationViewHelper.disableShiftMode(mbottom_navigation_view)
        mbottom_navigation_view.setOnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.navigation_home -> consume { System.out.println("navigation_home") }
                R.id.navigation_dashboard -> consume { System.out.println("navigation_dashboard") }
                R.id.navigation_notifications -> consume { System.out.println("navigation_notifications") }
                R.id.navigation_person -> consume { System.out.println("navigation_person") }
                else -> consume { System.out.println("other") }
            }
        }
    }

    private inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }


    private fun showNormalSnackbar() {
        Snackbar.make(window.decorView.findViewById(android.R.id.content), "showNormalSnackbar", Snackbar.LENGTH_SHORT)
            .show();  //和Toast很像
    }

    private fun showBtnSnackbar() {
        Snackbar.make(window.decorView.findViewById(android.R.id.content), "showBtnSnackbar", Snackbar.LENGTH_SHORT)
            .setAction(
                "action"
            ) { Toast.makeText(this@NormalViewsActivity, "snack bar ", Toast.LENGTH_SHORT).show() }.show()
    }
}