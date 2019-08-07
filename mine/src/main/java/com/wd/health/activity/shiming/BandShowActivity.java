package com.wd.health.activity.shiming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.presenter.BindCardPresenter;
import com.wd.health.utils.DisplayUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class BandShowActivity extends WDActivity implements View.OnClickListener {

    private EditText et_bank_name, et_card_type, et_card_number;
    private RelativeLayout rl_bk_back;
    private ImageView iv_mg;
    private Context context;
    private SharedPreferences sp;
    private Activity activity;
    private TextView iv_bank_sure;
    private BindCardPresenter bindCardPresenter;
    private String path;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_band;
    }

    @Override
    protected void initView() {
        bindCardPresenter = new BindCardPresenter(new BindCard());
        context = this;
        activity = this;
        sp = PreferenceManager.getDefaultSharedPreferences(context);

        initSet();

        Bundle extras = getIntent().getBundleExtra("bundle");

        if (extras != null) {
            path = extras.getString("img_path");
            String type = extras.getString("type");
            String bankCardNumber = extras.getString("bankCardNumber");
            String bankName = extras.getString("bankName");

            if (!TextUtils.isEmpty(path)) {
                try {

                    File file = new File(path);
                    FileInputStream inStream = null;

                    inStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(inStream);

                    iv_mg.setImageBitmap(bitmap);
                    inStream.close();

                    sp.edit().putString("bank_front", path).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            et_card_type.setText(type);
            et_bank_name.setText(bankName);
            et_card_number.setText(bankCardNumber);
            DisplayUtil.showSoftInputFromWindow(activity, et_bank_name);
        }
    }
    public class BindCard implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(context, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(context, ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void initSet() {
        iv_bank_sure = findViewById(R.id.iv_bank_sure);
        iv_mg = (ImageView) findViewById(R.id.iv_mg);
        et_bank_name = (EditText) findViewById(R.id.et_bank_name);
        et_card_type = (EditText) findViewById(R.id.et_card_type);
        et_card_number = (EditText) findViewById(R.id.et_card_number);
        rl_bk_back = (RelativeLayout) findViewById(R.id.rl_bk_back);
        rl_bk_back.setOnClickListener(this);
        iv_bank_sure.setOnClickListener(this);
    }

    @Override
    protected void destoryData() {

    }
    public int i;
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.rl_bk_back) {
            finish();

        } else if (i == R.id.iv_bank_sure) {
            final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
            if (loginBeans.size()>0){
                final LoginBean loginBean = loginBeans.get(0);
                final String name = et_bank_name.getText().toString();
                final String type = et_card_type.getText().toString();
                final String number = et_card_number.getText().toString();
                if (type.equals("借记卡")){
                    i=1;
                }else {
                    i=2;
                }
                bindCardPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),number,name,i);
            }



        } else {

        }
    }
}
