package com.bw.health.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.core.WDActivity;
import com.bw.health.fragment.BingzhengFragment;
import com.bw.health.fragment.YaopinFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/YpOrBzActivity/")
public class YpOrBzActivity extends WDActivity {


    @BindView(R2.id.head)
    ImageView head;
    @BindView(R2.id.messige)
    ImageView messige;
    @BindView(R2.id.bingzheng)
    RadioButton bingzheng;
    @BindView(R2.id.yaopin)
    RadioButton yaopin;
    @BindView(R2.id.frame)
    FrameLayout frame;
    private FragmentTransaction transaction;
    private BingzhengFragment bingzhengFragment;
    private YaopinFragment yaopinFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yp_or_bz;
    }

    @Override
    protected void initView() {
        transaction = getSupportFragmentManager().beginTransaction();
        bingzhengFragment = new BingzhengFragment();
        yaopinFragment = new YaopinFragment();
         int i = getIntent().getExtras().getInt("i");
        if (i==1){

            currentFragment = bingzhengFragment;
            bingzheng.setChecked(true);
        }else if (i==2){

            currentFragment=yaopinFragment;
            yaopin.setChecked(true);
        }

        transaction.add(R.id.frame, currentFragment)
                .show(currentFragment).commit();
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.head, R2.id.messige, R2.id.bingzheng, R2.id.yaopin})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.head) {

        } else if (i == R.id.messige) {

        } else if (i == R.id.bingzheng) {
            showFragment(bingzhengFragment);
        } else if (i == R.id.yaopin) {
            showFragment(yaopinFragment);
        } else {

        }
    }

    Fragment currentFragment;

    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.frame, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }
}
