package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.mine.R;
import com.wd.health.adapter.BuyVideoListAdapter;
import com.wd.health.adapter.CollectVideoAdapter;
import com.wd.health.bean.CollectVideoBean;
import com.wd.health.fragment.VideoFragment;
import com.wd.health.presenter.BuyVideoListPresenter;
import com.wd.health.presenter.CollectVideoPresenter;

import java.util.List;

public class MyVideoActivity extends WDActivity {

    XRecyclerView recycler;

    private int page=1;
    LinearLayout Null;
    private BuyVideoListPresenter buyVideoListPresenter;
    private BuyVideoListAdapter collectVideoAdapter;

    private ImageView back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_video;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        back=findViewById(R.id.back);
        recycler=findViewById(R.id.xrecycler);
        Null=findViewById(R.id.Null);
        buyVideoListPresenter = new BuyVideoListPresenter(new BuyVideoList());
        recycler.setLayoutManager(new LinearLayoutManager(MyVideoActivity.this,LinearLayoutManager.VERTICAL,false));
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }




    @Override
    public void onResume() {
        super.onResume();
        List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size() <= 0) {
            intentByRouter("/LoginActivity/");

        } else {
            LoginBean user = loginBeans.get(0);
            collectVideoAdapter = new BuyVideoListAdapter(MyVideoActivity.this);
            recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    buyVideoListPresenter.reqeust(user.getId(), user.getSessionId(), page, 10);
                }

                @Override
                public void onLoadMore() {
                    page+=1;
                    buyVideoListPresenter.reqeust(user.getId(), user.getSessionId(), page, 10);
                }
            });
            recycler.setAdapter(collectVideoAdapter);
            recycler.refresh();
        }
    }

    public class BuyVideoList implements DataCall<Result<List<CollectVideoBean>>>{
        @Override
        public void success(Result<List<CollectVideoBean>> data, Object... args) {
             List<CollectVideoBean> result = data.getResult();
            if (page==1&&result.size()<=0){
                Null.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            }else {
                Null.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
            }
            if (page==1){
                collectVideoAdapter.clear();
            }
            recycler.loadMoreComplete();
            recycler.refreshComplete();
            collectVideoAdapter.setList(data.getResult());
            collectVideoAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }else {
                Toast.makeText(MyVideoActivity.this, ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void destoryData() {

    }
}
