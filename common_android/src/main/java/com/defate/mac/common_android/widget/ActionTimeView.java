package com.defate.mac.common_android.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.defate.mac.common_android.R;

/**
 *  码表盘绘制view
 */
public class ActionTimeView extends View {
    // 画圆弧的画笔
    private Paint paint;
    // 正方形的宽高
    private int len;
    // 圆弧的半径
    private float radius;
    // 矩形
    private RectF oval;

    // 圆弧的经过总范围角度角度
    private float sweepAngle = 360;   //画整个圆

    private int key = 0;

    private float paddingLay = 40; //靠边的距离
    private float textLay = 15;  //坐标离圆心距离  值越大越近

    //图片宽度
    private int width = 0;
    //图片高度
    private int hight = 0;

    // 绘制文字
    Paint textPaint;

    public ActionTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setAntiAlias(true);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 通过测量规则获得宽和高
        width = MeasureSpec.getSize(widthMeasureSpec);
        hight = MeasureSpec.getSize(heightMeasureSpec);
        // 取出最小值
        len = Math.min(width, hight);
        oval = new RectF(0, 0, len, len);
        radius = len / 2;
        setMeasuredDimension(len, len);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画刻度线
        drawLine(canvas, key);
        // 画刻度线内的内容
        drawText(canvas);
    }

    /**
     * 实现画刻度线内的内容
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint cPaint = new Paint();
        // cPaint.setARGB(50, 236, 241, 243);
        cPaint.setAlpha(50);
        cPaint.setARGB(50, 236, 241, 243);
        // 画圆形背景
        RectF smalloval = new RectF(40, 40, radius * 2 - 40, radius * 2 - 40);
        // 画水波
//        drawWaterView(canvas);
        canvas.drawArc(smalloval, 0, 360, true, cPaint);
        // 在小圆圈的外围画一个白色圈
        // canvas.drawArc(smalloval, 0, 360, false, paint);
        // 设置文本对齐方式，居中对齐
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        textPaint.setColor(getResources().getColor(R.color.colorPrimary));
        // 画分数
        canvas.drawText("0", radius, 0 + textLay + 12, textPaint);  //12点钟方向的
        canvas.drawText("10", (float) (radius + radius * Math.sin(Math.PI / 5)) - textLay, (float) (radius - radius * Math.cos(Math.PI / 5)) + textLay, textPaint);
        canvas.drawText("20", (float) (radius + radius * Math.cos(Math.PI / 10)) - textLay, (float) (radius - radius * Math.sin(Math.PI / 10)) + textLay, textPaint);
        canvas.drawText("30", (float) (radius + radius * Math.cos(Math.PI / 10)), (float) (radius + radius * Math.sin(Math.PI / 10)), textPaint);
        canvas.drawText("40", (float) (radius + radius * Math.sin(Math.PI / 5)), (float) (radius + radius * Math.cos(Math.PI / 5)), textPaint);
        canvas.drawText("50", radius, 2 * radius - textLay + 12, textPaint);
        canvas.drawText("60", (float) (radius - radius * Math.sin(Math.PI / 5)) + textLay, (float) (radius + radius * Math.cos(Math.PI / 5)) + textLay, textPaint);
        canvas.drawText("70", (float) (radius - radius * Math.cos(Math.PI / 10)) + textLay, (float) (radius + radius * Math.sin(Math.PI / 10)) + textLay, textPaint);
        canvas.drawText("80", (float) (radius - radius * Math.cos(Math.PI / 10)) + textLay, (float) (radius - radius * Math.sin(Math.PI / 10)) + textLay, textPaint);
        canvas.drawText("90", (float) (radius - radius * Math.sin(Math.PI / 5)) + textLay, (float) (radius - radius * Math.cos(Math.PI / 5)) + textLay, textPaint);
    }

    float a = sweepAngle / 100;
    private Paint linePaint;

    /**
     * 实现画刻度线的功能
     *
     * @param canvas
     */
    private void drawLine(final Canvas canvas, int key) {
        // 保存之前的画布状态
        canvas.save();
        // 移动画布，实际上是改变坐标系的位置
        canvas.translate(radius, radius);
        // 旋转坐标系,需要确定旋转角度
        canvas.rotate(0);
        // 初始化画笔
        linePaint = new Paint();

        // 设置画笔的宽度（线的粗细） （2πr - 99*5 /100）
        double linewidth = (Math.PI * width - 99 * 8) / 100;

        // 设置抗锯齿
        linePaint.setAntiAlias(true);
        // 累计叠加的角度
        float c = 0;
        for (int i = 0; i <= 100; i++) {
            if (i % 10 == 0) {
                // 计算累计划过的刻度百分比（画过的刻度比上中共进过的刻度）
                double p = c / (double) sweepAngle;
                linePaint.setColor(getResources().getColor(R.color.colorPrimary));
                linePaint.setStrokeWidth((float) linewidth / 2);
                canvas.drawLine(0, radius - paddingLay, 0, radius - paddingLay - 25, linePaint);
                linePaint.setStrokeWidth((float) linewidth);
                canvas.drawLine(0, radius - paddingLay - 25, 0, radius - paddingLay - 50, linePaint);
                // 画过的角度进行叠加
                c += a;
            } else {
                linePaint.setStrokeWidth((float) linewidth);
                double p = c / (double) sweepAngle;
                linePaint.setColor(getResources().getColor(R.color.colorPrimary));
                canvas.drawLine(0, radius - paddingLay - 25, 0, radius - paddingLay - 50, linePaint);
                // 画过的角度进行叠加
                c += a;
            }

            if (i == key) {
                linePaint.setStrokeWidth((float) linewidth);
                double p = c / (double) sweepAngle;
                linePaint.setColor(getResources().getColor(R.color.colorAccent));
                canvas.drawLine(0, radius - paddingLay - 25, 0, radius - paddingLay - 50, linePaint);
                // 画过的角度进行叠加
                c += a;
            }
            canvas.rotate(a);
        }
        // 恢复画布状态。
        canvas.restore();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        //先处理key  50对应的是 0  0对应的是50
        if (key >= 0 && key <= 50) {
            this.key = key + 50;
        } else if (key > 50 && key <= 100) {
            this.key = key - 50;
        }
        invalidate();
    }
}

