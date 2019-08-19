package com.wd.health.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
import com.wd.health.presenter.PerfectUserInfoPresenter;
import com.wd.health.view.Qipao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.wancheng)
    Button wancheng;
    @BindView(R2.id.qipao)
    Qipao qipao;
    @BindView(R2.id.shengaoseekbar)
    SeekBar shengaoseekbar;
    @BindView(R2.id.qipao2)
    Qipao qipao2;
    @BindView(R2.id.tizhongseekbar)
    SeekBar tizhongseekbar;
    @BindView(R2.id.qipao3)
    Qipao qipao3;
    @BindView(R2.id.ageseekbar)
    SeekBar ageseekbar;
    private PerfectUserInfoPresenter perfectUserInfoPresenter;
    private List<LoginBean> list;
    private int age;
    private int height;
    private int weight;
    private DoTaskPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        TextView text = qipao.findViewById(R.id.tv);
        TextView text2 = qipao2.findViewById(R.id.tv);
        TextView text3 = qipao3.findViewById(R.id.tv);
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        age = list.get(0).getAge();
        height = list.get(0).getHeight();
        weight = list.get(0).getWeight();
        shengaoseekbar.setProgress((height -50)/2);
        text.setText(height +"cm");
        setDistance(qipao,(height -50)/2);
        tizhongseekbar.setProgress((int)((weight -30)*100/120));
        text2.setText(weight +"kg");
        setDistance(qipao2,(weight -30)*100/120);
        ageseekbar.setProgress((int)(age -18)*100/102);
        text3.setText(weight +"岁");
        setDistance(qipao3,(age -18)*100/102);
        perfectUserInfoPresenter = new PerfectUserInfoPresenter(new PerfectUserInfo());
        presenter = new DoTaskPresenter(new DoTask());
        shengaoseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDistance(qipao,progress);
                height=50 + progress * 2;
                text.setText(height + "cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tizhongseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDistance(qipao2,progress);
                weight=30 + progress *120/100;
                text2.setText(weight+ "kg");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ageseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDistance(qipao3,progress);
                age=18 + progress *102/100;
                text3.setText( age+ "岁");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    /**
     *
     * 设置距离左边margin
     *
     * */
    public void setDistance(Qipao qipao,int progress){
        ViewGroup.LayoutParams params = qipao.getLayoutParams();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(params);
        int i = (int) progress * (1080 - 290) / 100;
        Log.d("SignActivity2", "progress:" + progress);
        layoutParams.setMargins(i + 75, 60, 0, 0);
        qipao.setLayoutParams(layoutParams);
    }
    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back, R2.id.wancheng})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.wancheng) {
            perfectUserInfoPresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId(),age,height,weight);
        }
    }
    public class PerfectUserInfo implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            list.get(0).setAge(age);
            list.get(0).setHeight(height);
            list.get(0).setWeight(weight);
            GetDaoUtil.getGetDaoUtil().getUserDao().insertOrReplace(list.get(0));
            Result result= (Result) data;
            Toast.makeText(SignActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(),1004);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class DoTask implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            Toast.makeText(SignActivity.this,  result.getResult().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
