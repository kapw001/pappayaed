package com.pappayaed.splash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yasar on 2/5/17.
 */

public class ThreeDotsLoader extends View {

    Paint dot1;
    Paint dot2;
    Paint dot3;

    Rect rect1;
    Rect rect2;
    Rect rect3;

    int defaultColor;
    int highlightColor;

    boolean animationStarted = false;

    TimerTask task;
    Timer timer;

    int count = 1;

    public ThreeDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultColor = Color.WHITE; //Color.rgb(44, 78, 82);
        highlightColor = Color.WHITE;

        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("custom", "ondraw");
        switch (count) {
            case 1:
                canvas.drawCircle(50, 50, 10, dot1);
                canvas.drawCircle(100, 50, 15, dot2);
                canvas.drawCircle(150, 50, 15, dot3);
                break;
            case 2:
                canvas.drawCircle(50, 50, 15, dot1);
                canvas.drawCircle(100, 50, 10, dot2);
                canvas.drawCircle(150, 50, 15, dot3);
                break;
            case 3:
                canvas.drawCircle(50, 50, 15, dot1);
                canvas.drawCircle(100, 50, 15, dot2);
                canvas.drawCircle(150, 50, 10, dot3);
                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initPaint() {
        timer = new Timer();
        dot1 = new Paint();
        dot1.setColor(defaultColor);
        dot1.setStyle(Paint.Style.FILL);
        dot1.setAntiAlias(true);


        dot2 = new Paint();
        dot2.setColor(defaultColor);
        dot2.setStyle(Paint.Style.FILL);
        dot2.setAntiAlias(true);

        dot3 = new Paint();
        dot3.setColor(defaultColor);
        dot3.setStyle(Paint.Style.FILL);
        dot3.setAntiAlias(true);
    }

    public void startLoading() {
        task = new TimerTask() {

            @Override
            public void run() {

                switch (count) {
                    case 1:
//                        dot1.setColor(highlightColor);
//                        dot2.setColor(defaultColor);
//                        dot3.setColor(defaultColor);
                        count++;
                        break;
                    case 2:
//                        dot1.setColor(defaultColor);
//                        dot2.setColor(highlightColor);
//                        dot3.setColor(defaultColor);
                        count++;
                        break;
                    case 3:
//                        dot1.setColor(defaultColor);
//                        dot2.setColor(defaultColor);
//                        dot3.setColor(highlightColor);
                        count = 1;
                        break;
                }
                animationStarted = true;
                postInvalidate();
            }
        };
        timer.schedule(task, 0, 600);

    }

    public void stopLoading() {
        animationStarted = false;
        timer.cancel();
    }
}