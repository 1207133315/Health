package com.wd.health.activity.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.core.WDActivity;
import com.wd.health.R;
@Route(path = "/IMActivity/")
public class IMActivity extends WDActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }
}
