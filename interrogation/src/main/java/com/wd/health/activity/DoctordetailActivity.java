package com.wd.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.interrogation.R;
import com.wd.health.interrogation.R2;
import com.wd.health.adapter.PingjiaAdapter;
import com.wd.health.bean.DoctordetailBean;
import com.wd.health.presenter.CancelFollowPresenter;
import com.wd.health.presenter.FindDoctorInfoPresenter;
import com.wd.health.presenter.FollowDoctorPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

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
    CheckBox xin;
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
    @BindView(R2.id.jiaqian)
    TextView jiaqian;
    private FindDoctorInfoPresenter presenter;
    private List<LoginBean> list;
    private int id;
    private int j = 1;
    private int size = 0;
    private FollowDoctorPresenter followDoctorPresenter;
    private CancelFollowPresenter cancelFollowPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctordetail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("bean", -1);
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        Long id1 = list.get(0).getId();
        followDoctorPresenter=new FollowDoctorPresenter(new FollowDoctor());
        cancelFollowPresenter = new CancelFollowPresenter(new CancelFollow());
        Log.d("aaa", "initView: " + id1 + "/" + list.get(0).getSessionId());
        if (id > -1) {
            presenter = new FindDoctorInfoPresenter(new FindDoctorInfo());
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), id);
        }
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.back, R2.id.xin})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.xin) {
            if (xin.isChecked()){
                followDoctorPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), id);
            }else {
                cancelFollowPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), id);

            }
        }
    }
    public class FollowDoctor implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            xin.setBackgroundResource(R.mipmap.common_icon_attention_large_s);
            Result result= (Result) data;
            Toast.makeText(DoctordetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class CancelFollow implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            xin.setBackgroundResource(R.mipmap.common_icon_attention_large_n);
            Result result= (Result) data;
            Toast.makeText(DoctordetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class FindDoctorInfo implements DataCall {


        @Override
        public void success(Object data, Object... args) {
            Result<DoctordetailBean> beanResult = (Result<DoctordetailBean>) data;
            String imagePic = beanResult.getResult().getImagePic();
            String doctorName = beanResult.getResult().getDoctorName();
            String jobTitle = beanResult.getResult().getJobTitle();
            String inauguralHospital = beanResult.getResult().getInauguralHospital();
            String praise = beanResult.getResult().getPraise();
            int serverNum = beanResult.getResult().getServerNum();
            int servicePrice = beanResult.getResult().getServicePrice();
            int followFlag = beanResult.getResult().getFollowFlag();
            if (followFlag==1){
                xin.setChecked(true);
                xin.setBackgroundResource(R.mipmap.common_icon_attention_large_s);
            }
            jiaqian.setText(servicePrice + "H币/次");
            List<DoctordetailBean.DoctorReceiveGiftList> giftList = beanResult.getResult().getDoctorReceiveGiftList();
            String personalProfile = beanResult.getResult().getPersonalProfile();
            String goodField = beanResult.getResult().getGoodField();
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
            fuwu.setText("服务患者数 " + serverNum);
            if (giftList != null && giftList.size() > 2) {
                int worth = giftList.get(2).getWorth();
                cup.setText(worth + "");
            } else if (giftList != null && giftList.size() > 1) {
                shu.setText(giftList.get(1).getWorth() + "");
            } else if (giftList != null && giftList.size() > 0) {
                flower.setText(giftList.get(0).getWorth() + "");
            }
            if (personalProfile != null && personalProfile != "") {
                jianjie.setText(personalProfile);
            }
            if (goodField != null && goodField != "") {
                shanchang.setText(goodField);
            }
            List<DoctordetailBean.CommentListBean> commentList = beanResult.getResult().getCommentList();
            if (commentList != null && commentList.size() > 0) {
                pingjia.setText(" (" + commentList.size() + "条评价)");
                recy.setLayoutManager(new LinearLayoutManager(DoctordetailActivity.this, RecyclerView.VERTICAL, false));
                List<DoctordetailBean.CommentListBean> list = beanResult.getResult().getCommentList();
                ArrayList<DoctordetailBean.CommentListBean> commentListBeans = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    if (i < (3 * j)) {
                        commentListBeans.add(list.get(i));
                    }
                }
                size = commentListBeans.size();
                PingjiaAdapter adapter = new PingjiaAdapter(R.layout.pingjiaitem, commentListBeans);
                View view = View.inflate(DoctordetailActivity.this, R.layout.foot, null);
                TextView jiazai = view.findViewById(R.id.jiazai);
                ProgressBar progress = view.findViewById(R.id.bar);
                adapter.addFooterView(view);
                recy.setAdapter(adapter);
                recy.addItemDecoration(new DividerItemDecoration(DoctordetailActivity.this, DividerItemDecoration.VERTICAL));
                jiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jiazai.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        j++;
                        commentListBeans.removeAll(commentListBeans);
                        for (int i = 0; i < list.size(); i++) {
                            if (i < (3 * j)) {
                                commentListBeans.add(list.get(i));
                            }
                        }
                        if (size == commentListBeans.size()) {
                            Toast.makeText(DoctordetailActivity.this, "没有更多评论", Toast.LENGTH_SHORT).show();
                            jiazai.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                        } else {

                            adapter.notifyDataSetChanged();
                            jiazai.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                        }

                    }
                });
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
