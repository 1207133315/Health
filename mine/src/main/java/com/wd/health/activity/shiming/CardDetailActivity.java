package com.wd.health.activity.shiming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.bean.BankCardBean;
import com.wd.health.presenter.FindUserBankCardByUserIdPresenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import freemarker.template.utility.DateUtil;

public class CardDetailActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.hh)
    RelativeLayout hh;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.type)
    TextView type;
    @BindView(R2.id.num1)
    TextView num1;
    @BindView(R2.id.num2)
    TextView num2;
    @BindView(R2.id.time)
    TextView time;
    @BindView(R2.id.yes)
    TextView yes;
    private FindUserBankCardByUserIdPresenter findUserBankCardByUserIdPresenter;
    private LoginBean loginBean;
    private Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_detail;
    }

    @Override
    protected void initView() {
        findUserBankCardByUserIdPresenter = new FindUserBankCardByUserIdPresenter(new UserBankCard());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
        Long id = loginBean.getId();
        findUserBankCardByUserIdPresenter.reqeust( id.intValue(), loginBean.getSessionId());
    }

    @Override
    protected void destoryData() {
       findUserBankCardByUserIdPresenter.dataCall=null;
       findUserBankCardByUserIdPresenter=null;
        loginBean=null;
         intent=null;
    }

    public class UserBankCard implements DataCall<Result<BankCardBean>>{
        @Override
        public void success(Result<BankCardBean> data, Object... args) {
             BankCardBean result = data.getResult();
            name.setText(result.getBankName());
            final int bankCardType = result.getBankCardType();
            if (bankCardType==1){
                type.setText("借记卡");
            }else {
                type.setText("储蓄卡");
            }
             String bankCardNumber = result.getBankCardNumber();
             num1.setText(bankCardNumber.substring(3,8));
             num2.setText(bankCardNumber.substring(bankCardNumber.length()-4,bankCardNumber.length()));
             long bindTime = result.getBindTime();
             String s = DateUtils.timeStamp2Date(bindTime, "yyyy-MM-dd");
            time.setText("绑卡时间:"+s);

        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(CardDetailActivity.this, ""+data.getDisplayMessage()+data.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R2.id.back, R2.id.yes})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.yes) {
            intent = new Intent(CardDetailActivity.this, BindCardActivity.class);
            startActivity(intent);
            finish();
        } else {
        }
    }
}
