package com.bw.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = "HomeActivity")
public class HomeActivity extends WDActivity {


    @BindView(R2.id.frame)
    FrameLayout frame;
    @BindView(R2.id.home)
    RadioButton home;
    @BindView(R2.id.video)
    RadioButton video;
    @BindView(R2.id.circle)
    RadioButton circle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        final boolean checked = home.isChecked();
        Toast.makeText(this, ""+checked, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void destoryData() {

    }





    @SuppressLint("InvalidR2Usage")
    @OnClick({R2.id.home, R2.id.video, R2.id.circle})
    public void onClick(View v) {
      if (v.getId()==R.id.home){
          home.setChecked(true);
          circle.setChecked(false);
      }else if (v.getId()==R.id.video){
          video.setChecked(true);
          circle.setChecked(false);
      }else if (v.getId()==R.id.circle){
          circle.setChecked(true);
          home.setChecked(false);
          video.setChecked(false);
      }
    }
}
