package com.wd.health;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/30 08:34
 */
public class MyView extends RelativeLayout {
    private static final String TAG ="myView";

    public MyView(Context context) {
        
        super(context);
      //  Log.i(TAG, "onTouchEvent: one");
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
       // Log.i(TAG, "onTouchEvent: one");
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // Log.i(TAG, "onTouchEvent: one");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: ");
        return true;
    }
}
