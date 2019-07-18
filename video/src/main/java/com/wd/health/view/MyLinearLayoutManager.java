package com.wd.health.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.wd.health.view
 *
 * @author 李宁康
 * @date 2019 2019/07/17 17:19
 */
public class MyLinearLayoutManager extends LinearLayoutManager {

    private ILayoutCompleteListener mListener;

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        int count = getChildCount();
        int itemsHeight = 0;
        for (int i = 0; i < count; i++) {
            View item = getChildAt(i);
            if (item == null) {
                continue;
            }
            itemsHeight += item.getMeasuredHeight();
        }
        if (mListener != null) {
            mListener.onLayoutCompleted(itemsHeight);
        }
    }

    public void setLayoutCompleteListener(ILayoutCompleteListener listener) {
        mListener = listener;
    }

    public interface ILayoutCompleteListener {
        void onLayoutCompleted(int itemsHeight);
    }
}

