package com.bw.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.BZDetailBean;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.BZDetailPresenter;
import com.bw.health.util.GetDaoUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BZDetailActivity extends WDActivity {


    @BindView(R2.id.head)
    ImageView head;
    @BindView(R2.id.top)
    RelativeLayout top;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.bingli)
    TextView bingli;
    @BindView(R2.id.zhengzhuang)
    TextView zhengzhuang;
    @BindView(R2.id.yiorji)
    TextView yiorji;
    @BindView(R2.id.xiyao)
    TextView xiyao;
    @BindView(R2.id.zhongyao)
    TextView zhongyao;
    private long id;
    private BZDetailPresenter bzDetailPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bzdetail;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        id = intent.getLongExtra("id", 1);
       String  name = intent.getStringExtra("name");
        this.name.setText(name);
        bzDetailPresenter = new BZDetailPresenter(new BZDetail());
        bzDetailPresenter.reqeust(id);
    }


    public class BZDetail implements DataCall<Result<BZDetailBean>>{
        @Override
        public void success(Result<BZDetailBean> data, Object... args) {
             BZDetailBean result = data.getResult();
             bingli.setText(result.pathology);
             zhengzhuang.setText(result.symptom);
             yiorji.setText(result.benefitTaboo);
             if (null!=result.chineseMedicineTreatment){
                 zhongyao.setText(result.chineseMedicineTreatment);
             }else {
                 zhongyao.setText("无");
             }

             xiyao.setText(result.westernMedicineTreatment);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
        List<LoginBean> loginBeans = userDao.loadAll();
        if (loginBeans.size()>0){
            Glide.with(this).load(loginBeans.get(0).getHeadPic())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(head);
        }else {
            head.setImageResource(R.drawable.boy);
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
