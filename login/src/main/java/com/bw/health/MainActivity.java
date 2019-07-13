package com.bw.health;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.core.WDActivity;
import com.bw.login.R;
@Route(path = "/MainActivity/")
public class MainActivity extends WDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void destoryData() {

    }
}
