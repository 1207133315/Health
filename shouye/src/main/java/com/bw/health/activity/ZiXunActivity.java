package com.bw.health.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.shouye.R;
import com.bw.health.shouye.R2;
import com.bw.health.adapter.MationListAdapter;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.MationListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/ZiXunActivity/")
public class ZiXunActivity extends WDActivity {
    @BindView(R2.id.head)
    ImageView head;
    @BindView(R2.id.recycler)
    XRecyclerView recycler;
    @BindView(R2.id.dataNull)
    LinearLayout dataNull;
    @BindView(R2.id.title)
    TextView title;

    private MationListPresenter mationListPresenter;
    private MationListAdapter mationListAdapter;
    private int page=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_zi_xun;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        title.setText( bundle.getString("title"));
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mationListPresenter = new MationListPresenter(new MationList());
        mationListAdapter = new MationListAdapter(this);
        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                mationListPresenter.reqeust(id,page,10);
            }

            @Override
            public void onLoadMore() {
                page++;
                mationListPresenter.reqeust(id,page,10);
            }
        });
        recycler.setAdapter(mationListAdapter);
        recycler.refresh();

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy<0){
                    dataNull.setVisibility(View.GONE);
                }
            }
        });
    }


    public class MationList implements DataCall<Result<List<MationBean>>> {
        @Override
        public void success(Result<List<MationBean>> data, Object... args) {
            final List<MationBean> result = data.getResult();
            if (result.size()<=0){
                dataNull.setVisibility(View.VISIBLE);
            }else {
                dataNull.setVisibility(View.GONE);
            }
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).thumbnail != null) {
                    String[] split = result.get(i).thumbnail.split(";");

                        Log.i("lnk", "success: "+split.length);

                    if (split.length == 1) {
                        result.get(i).aa = 1;
                    } else if (split.length == 3) {
                        result.get(i).aa=2;
                    }
                }else {
                    result.get(i).aa=3;
                }

            }
            recycler.refreshComplete();
            recycler.loadMoreComplete();
            if (page==1){
                mationListAdapter.clear();
            }

            mationListAdapter.addList(result);
            mationListAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    protected void destoryData() {

    }



    @OnClick(R2.id.head)
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.head) {

        } else {

        }
    }
}
