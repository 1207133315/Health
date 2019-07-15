package com.wd.health;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.core.WDActivity;
@Route(path = "/MineActivity/")
public class MineActivity extends WDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }
}
