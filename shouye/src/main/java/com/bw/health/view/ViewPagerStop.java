package com.bw.health.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bw.health.util.Constant;

import androidx.viewpager.widget.ViewPager;

/**
 * com.bw.health.view
 *
 * @author 李宁康
 * @date 2019 2019/07/16 20:30
 */
public class ViewPagerStop extends ViewPager {
    private static final String TAG = "ViewPagerStop";
    public boolean result = false;

    public ViewPagerStop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerStop(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (result)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (result)
            return super.onTouchEvent(arg0);
        else
            return false;
    }

    private int downX,downY,moveX,moveY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downX = (int) ev.getX();
                downY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int diffX = moveX - downX;
                int diffY = moveY - downY;
                if(Math.abs(diffX) > Math.abs(diffY)) { // 当前是横向滑动, 不让父元素拦截
          getParent().requestDisallowInterceptTouchEvent(true);
                    //Constant.CLASHTAB 在一个常量类中初始化值为true;也就是   //Constant.CLASHTAB 初始值为true

                } else {
          getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;
            case MotionEvent.ACTION_UP:

       getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

