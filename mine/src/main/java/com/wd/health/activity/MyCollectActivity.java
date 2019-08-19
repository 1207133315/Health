package com.wd.health.activity;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bw.health.core.WDActivity;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.fragment.CircleFragment;

import com.wd.health.fragment.VideoFragment;
import com.wd.health.fragment.ZiXunFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCollectActivity extends WDActivity {
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.zixun)
    RadioButton zixun;
    @BindView(R2.id.shipin)
    RadioButton shipin;
    @BindView(R2.id.bingyouquan)
    RadioButton bingyouquan;
    @BindView(R2.id.frame)
    FrameLayout frame;
    private ZiXunFragment ziXunFragment;
    private VideoFragment videoFragment;
    private CircleFragment circleFragment;
    private FragmentTransaction transaction;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initView() {
        ziXunFragment = new ZiXunFragment();
        videoFragment = new VideoFragment();
        circleFragment = new CircleFragment();
        currentFragment=ziXunFragment;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame,ziXunFragment).show(ziXunFragment).commit();
        zixun.setChecked(true);
    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.back, R2.id.zixun, R2.id.shipin, R2.id.bingyouquan})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.zixun) {
            showFragment(ziXunFragment);
        } else if (i == R.id.shipin) {
            showFragment(videoFragment);
        } else if (i == R.id.bingyouquan) {
            showFragment(circleFragment);
        } else {
        }
    }
    private Fragment currentFragment;
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
