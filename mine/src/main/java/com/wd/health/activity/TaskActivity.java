package com.wd.health.activity;


import android.view.View;
import android.widget.ImageView;

import com.bw.health.core.WDActivity;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.adapter.StringAdapter;
import com.wd.health.view.Myprogressbar;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class TaskActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.recy1)
    RecyclerView recy1;
    @BindView(R2.id.progress)
    Myprogressbar progress;
    private StringAdapter stringAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        ArrayList<String> list = new ArrayList<>();
        list.add("+1");
        list.add("+2");
        list.add("+3");
        list.add("+4");
        list.add("+5");
        list.add("+10");
        list.add("+10");

        recy1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stringAdapter = new StringAdapter(R.layout.strings, list);
        recy1.setAdapter(stringAdapter);
        progress.setindex(4);
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        }
    }


}
