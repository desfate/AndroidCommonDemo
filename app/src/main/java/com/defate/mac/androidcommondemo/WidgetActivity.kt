package com.defate.mac.androidcommondemo

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.defate.mac.common_android.widget.address.AddressDialog
import kotlinx.android.synthetic.main.activity_widgets.*

/**
 * 用于展示控件的activity
 */
class WidgetActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets)
        action_time_view.key = 10
        showAddressDialog()
    }


    fun showAddressDialog(){
        val addressDialog = AddressDialog(this, R.style.Base_ThemeOverlay_AppCompat)
        addressDialog.setListener {

        }
        addressDialog.show()

        val dialogWindow = addressDialog.window
        val lp = dialogWindow.attributes
        lp.width = applicationContext.resources.displayMetrics.widthPixels
        lp.height = applicationContext.resources.displayMetrics.heightPixels * 2 / 3
        dialogWindow.setGravity(Gravity.BOTTOM)
        dialogWindow.attributes = lp
    }
}