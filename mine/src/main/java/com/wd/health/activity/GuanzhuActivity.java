package com.wd.health.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.presenter.FindUserDoctorFollowListPresenter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/GuanzhuActivity/")
public class GuanzhuActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.recy)
    RecyclerView recy;
    @BindView(R2.id.zanwu)
    LinearLayout zanwu;
    private FindUserDoctorFollowListPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guanzhu;
    }

    @Override
    protected void initView() {
        List<LoginBean> list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        presenter = new FindUserDoctorFollowListPresenter(new FindUserDoctorFollowList());
        if (list!=null&&list.size()>0){
            presenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId()+"",1,10);

        }

    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R2.id.back)
    public void onViewClicked() {

    }
    public class FindUserDoctorFollowList implements DataCall {

        @Override
        public void success(Object data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
