package com.bw.health.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.Result;
import com.bw.health.bean.YPDetailBean;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.YPDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YPDetailActivity extends WDActivity {
    @BindView(R2.id.head)
    ImageView head;
    @BindView(R2.id.top)
    RelativeLayout top;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.chengfen)
    TextView chengfen;
    @BindView(R2.id.jinji)
    TextView jinji;
    @BindView(R2.id.zhuzhi)
    TextView zhuzhi;
    @BindView(R2.id.yongliang)
    TextView yongliang;
    @BindView(R2.id.xingzhuang)
    TextView xingzhuang;
    @BindView(R2.id.guige)
    TextView guige;
    @BindView(R2.id.fanying)
    TextView fanying;
    @BindView(R2.id.tiaojian)
    TextView tiaojian;
    @BindView(R2.id.shixiang)
    TextView shixiang;
    @BindView(R2.id.wenhao)
    TextView wenhao;
    private long id;
    private YPDetailPresenter ypDetailPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ypdetail;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        id = intent.getLongExtra("id", 1);
        String name = intent.getStringExtra("name");
        this.name.setText(name);
        ypDetailPresenter = new YPDetailPresenter(new YPDetail());
        ypDetailPresenter.reqeust(id);
    }


    public class YPDetail implements DataCall<Result<YPDetailBean>>{
        @Override
        public void success(Result<YPDetailBean> data, Object... args) {
             YPDetailBean result = data.getResult();
             chengfen.setText(result.component);
             if (null!=result.taboo){
                 jinji.setText(result.taboo);
             }else {
                 jinji.setText("无");
             }
             zhuzhi.setText(result.effect);
             yongliang.setText(result.usage);
             xingzhuang.setText(result.shape);
             guige.setText(result.packing);
             fanying.setText(result.sideEffects);
             tiaojian.setText(result.storage);
             shixiang.setText(result.mindMatter);
             wenhao.setText(result.approvalNumber);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.head, R2.id.top})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.head) {
        } else if (i == R.id.top) {
        } else {
        }
    }
}
