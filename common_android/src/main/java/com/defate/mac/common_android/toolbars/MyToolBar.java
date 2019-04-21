package com.defate.mac.common_android.toolbars;

import android.content.Context;
import androidx.appcompat.widget.Toolbar;

/**
 * 一个专用区域，可以标识您的应用并指示用户在应用中的位置。
 * 以可预测的方式访问搜索等重要操作。
 * 支持导航和视图切换（通过标签页或下拉列表）。
 *
 * Toolbar 小部件能够在运行
 * Android 2.1（API 级别 7）或更高版本的设备上提供 Material Design 体验，
 * 但除非设备运行的是 Android 5.0（API 级别 21）或更高版本，否则原生操作栏不会支持 Material Design。
 */
public class MyToolBar extends Toolbar {

    public MyToolBar(Context context) {
        super(context);
    }
}
