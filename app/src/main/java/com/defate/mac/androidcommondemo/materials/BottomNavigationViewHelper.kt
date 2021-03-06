package com.defate.mac.androidcommondemo.materials

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.reflect.Field

class BottomNavigationViewHelper{
    companion object {
        @SuppressLint("RestrictedApi")
        fun disableShiftMode(view: BottomNavigationView){
            var menuView: BottomNavigationMenuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                var shiftingMode :Field = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (index in 0 until menuView.childCount){
                    var item: BottomNavigationItemView = menuView.getChildAt(index) as BottomNavigationItemView
                    item.setShifting(false)
                    item.setChecked(item.itemData.isChecked)
                }
            }catch (e:NoSuchFieldException ) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (e:IllegalAccessException) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}