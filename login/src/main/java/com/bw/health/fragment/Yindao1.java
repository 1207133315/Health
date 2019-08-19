package com.bw.health.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.bw.health.core.WDFragment;
import com.wd.health.login.R;
import com.wd.health.login.R2;


import butterknife.BindView;

public class Yindao1 extends WDFragment {


    @BindView(R2.id.yidaoimage)
    ImageView yidaoimage;
    @BindView(R2.id.yindao_text1)
    TextView yindaoText1;

    private int tupian;
    private String text;
    private TextView yindao_text1;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.yindao1;
    }

    public void setview1(int tupian, String text) {
        this.tupian = tupian;
        this.text = text;
    }

    @Override
    protected void initView() {

        yidaoimage.setImageResource(tupian);
        yindaoText1.setText(text);
    }
}
