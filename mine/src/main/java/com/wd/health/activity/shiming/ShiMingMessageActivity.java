package com.wd.health.activity.shiming;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.bean.IDCardBean;
import com.wd.health.presenter.FindUserIdCardPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiMingMessageActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.hh)
    RelativeLayout hh;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.type)
    TextView type;
    @BindView(R2.id.num)
    TextView num;
    private FindUserIdCardPresenter findUserIdCardPresenter;
    private LoginBean loginBean;
    private List<LoginBean> loginBeans;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shi_ming_message;
    }

    @Override
    protected void initView() {
        findUserIdCardPresenter = new FindUserIdCardPresenter(new IdCard());
        loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()>0){
            loginBean = loginBeans.get(0);
            findUserIdCardPresenter.reqeust(loginBean.getId().intValue(),loginBean.getSessionId());
        }else {
            intentByRouter("/LoginActivity/");
        }

    }


    public class IdCard implements DataCall<Result<IDCardBean>>{
        @Override
        public void success(Result<IDCardBean> data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }





    @Override
    protected void destoryData() {

    }


    @OnClick(R2.id.back)
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
        } else {
        }
    }
}
