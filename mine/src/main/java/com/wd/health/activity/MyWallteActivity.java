package com.wd.health.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.adapter.RecordListAdapter;
import com.wd.health.bean.RecordListBean;
import com.wd.health.presenter.MyHBPresenter;
import com.wd.health.presenter.RecordListPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyWallteActivity extends WDActivity implements View.OnClickListener {


    private ImageView back;
    /**
     * 200
     */
    private TextView HBnum;
    /**
     * 提现
     */
    private TextView reduce;
    /**
     * 充值
     */
    private TextView add;
    private RelativeLayout top;
    private RecordListPresenter recordListPresenter;
    private RecordListAdapter recordListAdapter;
    XRecyclerView recylcer;
    private MyHBPresenter myHBPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_wallte;
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        HBnum = (TextView) findViewById(R.id.HBnum);
        reduce = (TextView) findViewById(R.id.reduce);
        reduce.setOnClickListener(this);
        add = (TextView) findViewById(R.id.add);
        add.setOnClickListener(this);
        top = (RelativeLayout) findViewById(R.id.top);
        recylcer=findViewById(R.id.recycler);
        recordListPresenter = new RecordListPresenter(new RecordList());

        myHBPresenter = new MyHBPresenter(new MyHB());
    }
    private int page=1;
    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        super.onResume();
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()<=0){
            intentByRouter("/LoginActivity/");
        }else {
             LoginBean user = loginBeans.get(0);
            recordListAdapter = new RecordListAdapter();
            myHBPresenter.reqeust(user.getId(),user.getSessionId());

            recylcer.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            recylcer.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page=1;
                    recordListPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }

                @Override
                public void onLoadMore() {
                    page+=1;
                    recordListPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }
            });
            recylcer.setAdapter(recordListAdapter);
            recylcer.refresh();
        }
    }

    public class MyHB implements DataCall<Result<Integer>>{
        @Override
        public void success(Result<Integer> data, Object... args) {
             Integer result = data.getResult();
            final String s = String.valueOf(result);
            if (s.length()<5){
                HBnum.setText(data.getResult()+"");
            }else {
                 int i = Integer.valueOf(s) / 10000;
                HBnum.setText(i+"万");
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {
            if (data.getDisplayMessage().equals("请先登陆"))
                intentByRouter("LoginActivity");
        }
    }
    public class RecordList implements DataCall<Result<List<RecordListBean>>>{
        @Override
        public void success(Result<List<RecordListBean>> data, Object... args) {
            if (page==1){
                recordListAdapter.clear();
            }
            recylcer.refreshComplete();
            recylcer.loadMoreComplete();
            recordListAdapter.setList(data.getResult());
            recordListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

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
        } else if (i == R.id.reduce) {
        } else if (i == R.id.add) {
            startActivity(new Intent(MyWallteActivity.this,AddActivity.class));
        } else {
        }
    }
}
