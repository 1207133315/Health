package com.wd.doctor.interrogation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Myrecycler extends RecyclerView {


    public Myrecycler(@NonNull Context context) {
        super(context);
    }

    public Myrecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Myrecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
