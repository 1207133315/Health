package com.wd.health.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.wd.health.R;

public class Qipao extends RelativeLayout {
    public Qipao(Context context) {
        this(context,null);
    }

    public Qipao(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Qipao(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.qipao,this);
    }
}
