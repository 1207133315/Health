package com.wd.health.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.UpdateUserSexPresenter;
import com.wd.health.view.NV;
import com.wd.health.view.Nan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateSexActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.wancheng)
    Button wancheng;
    @BindView(R2.id.boyw)
    RadioButton boyw;
    @BindView(R2.id.girlw)
    RadioButton girlw;
    @BindView(R2.id.sexgroup)
    RadioGroup sexgroup;
    @BindView(R2.id.boyx)
    Nan boyx;
    @BindView(R2.id.girlx)
    NV girlx;
    private UpdateUserSexPresenter updateUserSexPresenter;
    private List<LoginBean> list;
    private int buttonId;
    private DoTaskPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_sex;
    }

    @Override
    protected void initView() {
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        int sex = list.get(0).getSex();
        updateUserSexPresenter = new UpdateUserSexPresenter(new UpdateUserSex());
        if (sex==1){
            sexgroup.check(R.id.boyw);
            boyx.setVisibility(View.VISIBLE);
            girlx.setVisibility(View.GONE);
        }else {
            sexgroup.check(R.id.girlw);
            boyx.setVisibility(View.GONE);
            girlx.setVisibility(View.VISIBLE);
        }
        presenter = new DoTaskPresenter(new DoTask());
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back, R2.id.wancheng, R2.id.boyw, R2.id.girlw})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.wancheng) {
            buttonId = sexgroup.getCheckedRadioButtonId();
            if (buttonId ==R.id.boyw){
                updateUserSexPresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId(),1);
            }else {
                updateUserSexPresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId(),2);
            }
        } else if (i == R.id.boyw) {
            boyx.setVisibility(View.VISIBLE);
            girlx.setVisibility(View.GONE);
        } else if (i == R.id.girlw) {
            boyx.setVisibility(View.GONE);
            girlx.setVisibility(View.VISIBLE);
        }
    }
    public class UpdateUserSex implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            if (buttonId ==R.id.boyw){
                list.get(0).setSex(1);
                GetDaoUtil.getGetDaoUtil().getUserDao().insertOrReplace(list.get(0));

            }else {
                list.get(0).setSex(2);
                GetDaoUtil.getGetDaoUtil().getUserDao().insertOrReplace(list.get(0));
            }
            Result result= (Result) data;
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(),1004);
            Toast.makeText(UpdateSexActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(UpdateSexActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public class DoTask implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            Toast.makeText(UpdateSexActivity.this,  result.getResult().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
