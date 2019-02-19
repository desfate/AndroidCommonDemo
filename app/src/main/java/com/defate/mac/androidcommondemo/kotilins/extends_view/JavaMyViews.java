package com.defate.mac.androidcommondemo.kotilins.extends_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import org.jetbrains.annotations.Nullable;

public class JavaMyViews extends View {

    public JavaMyViews(Context context) {
        super(context);
    }

    public JavaMyViews(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JavaMyViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JavaMyViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
