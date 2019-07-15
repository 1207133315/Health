package com.wd.health.frag;

import android.widget.Toast;

import com.bw.health.bean.CircleListBean;
import com.bw.health.core.WDFragment;
import com.wd.health.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FindSickCircleInfoFrag extends WDFragment {
    private CircleListBean circleListBean;
    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circleinfo_frag_layout;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getCircleListBean(CircleListBean circleListBean){
        this.circleListBean=circleListBean;
    }
}
