package com.wd.health.frag;

import android.widget.TextView;
import com.bw.health.core.WDFragment;
import com.wd.health.R;
import com.wd.health.R2;

import butterknife.BindView;


public class CircleFrag extends WDFragment {

    @BindView(R2.id.circle_frag_text1)
    TextView circle_frag_text1;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_frag_layout;

    }

    @Override
    protected void initView() {

    }
}
