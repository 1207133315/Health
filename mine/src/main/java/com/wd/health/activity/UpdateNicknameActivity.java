package com.wd.health.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.ModifyNickNamePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNicknameActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.wancheng)
    Button wancheng;
    @BindView(R2.id.nickname)
    EditText nickname;
    @BindView(R2.id.cha)
    ImageView cha;
    private ModifyNickNamePresenter modifyNickNamePresenter;
    private List<LoginBean> list;
    private DoTaskPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_nickname;
    }

    @Override
    protected void initView() {
        String name = getIntent().getStringExtra("name");
        nickname.setText(name);
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        modifyNickNamePresenter = new ModifyNickNamePresenter(new ModifyNickName());
        presenter = new DoTaskPresenter(new DoTask());
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.back, R2.id.wancheng, R2.id.cha})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.wancheng) {
            String s = nickname.getText().toString();
            if (s!=""){
                modifyNickNamePresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId(),s);
            }else {
                Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            }


        } else if (i == R.id.cha) {
            nickname.setText("");
        }
    }
    public class ModifyNickName implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            list.get(0).setNickName(nickname.getText().toString().trim());
            GetDaoUtil.getGetDaoUtil().getUserDao().insertOrReplace(list.get(0));
            Toast.makeText(UpdateNicknameActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(),1004);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(UpdateNicknameActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public class DoTask implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            Toast.makeText(UpdateNicknameActivity.this, "result.getResult():" + result.getResult(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
