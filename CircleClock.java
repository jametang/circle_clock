package com.example.android.architecture.blueprints.todoapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;


/**
 * 实现一个自定义view
 * 如果layout_heigt为固定值，或者为match_parent，则实际尺寸为固定值或者为match_parent
 * 如果layout_height为wrap_content,则实际尺寸为100固定值
 */
public class CircleClock extends View{

    private int defaultSize = 50;

    public CircleClock(Context context) {
        super(context);
    }

    public CircleClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CircleClock);
        defaultSize = array.getDimensionPixelSize(R.styleable.CircleClock_default_size,50);
        array.recycle();
        initView();
    }

    private void initView() {
    }

    public CircleClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = defaultSize;
        int height = defaultSize;
        int defaultWidth = MeasureSpec.getSize(widthMeasureSpec);
        int defaultHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? defaultWidth : width,
                heightMode == MeasureSpec.EXACTLY ? defaultHeight : height);

    }
    Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        int r = (getMeasuredHeight() + getMeasuredWidth())/4;
        int centerX = r;
        int centerY = r;
        canvas.drawCircle(centerX,centerY,r,mPaint);
        paintPointer(canvas,centerX,centerY);
        postInvalidateDelayed(1000);
    }


    private void paintPointer(Canvas canvas,int x,int y) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时
        int minute = calendar.get(Calendar.MINUTE); //分
        int second = calendar.get(Calendar.SECOND); //秒
        int angleHour = (hour % 12) * 360 / 12; //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度
        //secend
        canvas.save();
        canvas.rotate(angleSecond,x,y);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(x,0,x,y,mPaint);
        canvas.restore();
        //min
        canvas.save();
        canvas.rotate(angleMinute,x,y);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.drawLine(x,20,x,y,mPaint);
        canvas.restore();
        //hour
        canvas.save();
        canvas.rotate(angleHour,x,y);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        canvas.drawLine(x,40,x,y,mPaint);
        canvas.restore();
        //绘制中心小圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, 10, mPaint);
    }
}
