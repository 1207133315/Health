package com.bw.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.HomeFrag;
import com.bw.health.core.WDActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private FragmentTransaction transaction;
    private HomeFrag homeFrag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    Fragment currentFragment;
    @Override
    protected void initView() {
        homeFrag = new HomeFrag();
        currentFragment=homeFrag;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame,homeFrag).show(homeFrag).commit();
        home.setChecked(true);
    }

    @Override
    protected void destoryData() {

    }





    @SuppressLint("InvalidR2Usage")
    @OnClick({R2.id.home, R2.id.video, R2.id.circle})
    public void onClick(View v) {
      if (v.getId()==R.id.home){
          circle.setChecked(false);
          showFragment(homeFrag);
      }else if (v.getId()==R.id.video){
          circle.setChecked(false);
      }else if (v.getId()==R.id.circle){
          home.setChecked(false);
          video.setChecked(false);
      }
    }

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
