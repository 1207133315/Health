package com.wd.health.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.presenter.AddMoneyPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



public class AddActivity extends WDActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * ￥
     */
    private TextView mBiao;
    private EditText mNum;
    /**
     * 300
     */
    private TextView mJine;
    private ImageView mWeixin;
    /**
     * 微信支付
     */
    private TextView mWeixintext;
    private ImageView mZhifubao;
    private RadioButton mWeibtn;
    private RadioButton mZhibtn;
    private RadioGroup mRadiogroup;
    /**
     * 充值
     */
    private TextView mAdd;
    private AddMoneyPresenter addMoneyPresenter;
    private LoginBean loginBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }
    private int WXorZFB;
    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mBiao = (TextView) findViewById(R.id.biao);
        mBiao.setOnClickListener(this);
        mNum = (EditText) findViewById(R.id.num);
        mNum.setOnClickListener(this);
        mJine = (TextView) findViewById(R.id.jine);
        mJine.setOnClickListener(this);
        mWeixin = (ImageView) findViewById(R.id.weixin);
        mWeixin.setOnClickListener(this);
        mWeixintext = (TextView) findViewById(R.id.weixintext);
        mWeixintext.setOnClickListener(this);
        mZhifubao = (ImageView) findViewById(R.id.zhifubao);
        mZhifubao.setOnClickListener(this);
        mWeibtn = (RadioButton) findViewById(R.id.weibtn);
        mWeibtn.setOnClickListener(this);
        mZhibtn = (RadioButton) findViewById(R.id.zhibtn);
        mZhibtn.setOnClickListener(this);
        mRadiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        mRadiogroup.setOnClickListener(this);
        mAdd = (TextView) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                 String s1 = mNum.getText().toString();
                 if (s1.length()>0||!s1.equals("")) {
                     double v = Double.parseDouble(s1);
                     mJine.setText(v * 100 + "");
                 }else {
                     mJine.setText(0+"");
                 }
            }
        });
        List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        loginBean = loginBeans.get(0);
        addMoneyPresenter = new AddMoneyPresenter(new AddMoney());

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = mRadiogroup.getCheckedRadioButtonId();
                if (i == R.id.weibtn) {
                    WXorZFB=1;

                } else if (i == R.id.zhibtn) {
                    WXorZFB=2;
                    addMoneyPresenter.reqeust(loginBean.getId(), loginBean.getSessionId(),10,2);
                    Log.i("id", "onClick: "+loginBean.getId());
                } else {
                    Toast.makeText(AddActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1000){
                String result= (String) msg.obj;//支付后返回的信息
                Log.i("zhifubao", "TestActivity: "    + result);
                Toast.makeText(AddActivity.this, ""+result, Toast.LENGTH_SHORT).show();
            }
        }
    };
    //调起支付宝
    private void pay(String data) {
        String orderInfo = data;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(AddActivity.this);
                String result = alipay.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj=result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public class AddMoney implements DataCall<Result<String>>{
        @Override
        public void success(Result<String> data, Object... args) {
                pay(data.getResult());
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Log.i("cz", "fail: "+data.getDisplayMessage());
        }
    }
    @Override
    protected void destoryData() {

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.biao) {
        } else if (i == R.id.num) {
        } else if (i == R.id.jine) {
        } else if (i == R.id.weixin) {
        } else if (i == R.id.weixintext) {
        } else if (i == R.id.zhifubao) {
        } else if (i == R.id.weibtn) {
        } else if (i == R.id.zhibtn) {
        } else if (i == R.id.radiogroup) {
        } else if (i == R.id.add) {

        } else {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler=null;
    }
}
