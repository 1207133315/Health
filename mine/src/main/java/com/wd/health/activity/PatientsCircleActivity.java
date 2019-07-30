package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.core.WDActivity;
import com.wd.health.R;
@Route(path = "/PatientsCircleActivity/")
public class PatientsCircleActivity extends WDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patients_circle;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }
}
