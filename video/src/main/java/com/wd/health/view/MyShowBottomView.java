package com.wd.health.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * com.wd.health.view
 *
 * @author 李宁康
 * @date 2019 2019/07/19 20:04
 */
public class MyShowBottomView extends AppCompatImageView {
    public MyShowBottomView(Context context) {
        super(context);
    }

    public MyShowBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyShowBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        //请求所有父控件不要拦截Touch事件
       // getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }*/
}
