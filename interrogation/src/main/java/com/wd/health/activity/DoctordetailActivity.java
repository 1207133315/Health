package com.wd.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.bean.DoctordetailBean;
import com.wd.health.presenter.FindDoctorInfoPresenter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/DoctordetailActivity/")
public class DoctordetailActivity extends WDActivity {

    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.img)
    ImageView img;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.zhiwu)
    TextView zhiwu;
    @BindView(R2.id.xin)
    ImageView xin;
    @BindView(R2.id.yiyuan)
    TextView yiyuan;
    @BindView(R2.id.haopinlv)
    TextView haopinlv;
    @BindView(R2.id.flower)
    TextView flower;
    @BindView(R2.id.shu)
    TextView shu;
    @BindView(R2.id.cup)
    TextView cup;
    @BindView(R2.id.jianjie)
    TextView jianjie;
    @BindView(R2.id.shanchang)
    TextView shanchang;
    @BindView(R2.id.pingjia)
    TextView pingjia;
    @BindView(R2.id.recy)
    RecyclerView recy;
    @BindView(R2.id.zixun)
    Button zixun;
    @BindView(R2.id.fuwu)
    TextView fuwu;
    private FindDoctorInfoPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctordetail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("bean", -1);
        List<LoginBean> list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        Long id1 = list.get(0).getId();
        Log.d("aaa", "initView: " + id1 + "/" + list.get(0).getSessionId());
        if (id > -1) {
            presenter = new FindDoctorInfoPresenter(new FindDoctorInfo());
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), id);
        }
    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R2.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class FindDoctorInfo implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result<DoctordetailBean> beanResult = (Result<DoctordetailBean>) data;
            String imagePic = beanResult.getResult().getImagePic();
            String doctorName = beanResult.getResult().getDoctorName();
            String jobTitle = beanResult.getResult().getJobTitle();
            String inauguralHospital = beanResult.getResult().getInauguralHospital();
            double praise = beanResult.getResult().getPraise();
            int serverNum = beanResult.getResult().getServerNum();
            if (imagePic != null) {
                Glide.with(DoctordetailActivity.this).load(imagePic).into(img);
            }
            if (doctorName != "" && doctorName != null) {
                name.setText(doctorName);
            }
            if (jobTitle != null && jobTitle != "") {
                zhiwu.setText(jobTitle);
            }
            if (inauguralHospital != "" && inauguralHospital != null) {
                yiyuan.setText(inauguralHospital);
            }
            haopinlv.setText("好评率 " + praise);
            fuwu.setText("服务患者数 "+serverNum);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
