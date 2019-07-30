package com.wd.health;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/30 08:40
 */
public class MyView2 extends AppCompatTextView {
    private static final String TAG = "MyView2";

    public MyView2(Context context) {

        super(context);
        //Log.i(TAG, "onTouchEvent: text2");
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       // Log.i(TAG, "onTouchEvent: text2");
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // Log.i(TAG, "onTouchEvent: text2");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchHoverEvent: ");
        return super.dispatchTouchEvent(event);
    }
}
