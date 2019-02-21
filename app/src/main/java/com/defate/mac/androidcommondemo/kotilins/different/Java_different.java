package com.defate.mac.androidcommondemo.kotilins.different;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import com.defate.mac.androidcommondemo.R;

public class Java_different {

    /**
     * 这是java的写法
     * @param context
     */
    public static void drawableTest(Context context){
        Resources res = context.getResources();
        Drawable myImages = ResourcesCompat.getDrawable(res, R.drawable.ic_alarm_white_48dp, null);
    }
}
