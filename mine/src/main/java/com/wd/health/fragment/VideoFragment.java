package com.wd.health.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.adapter.CollectVideoAdapter;
import com.wd.health.adapter.MationListAdapter;
import com.wd.health.bean.CollectVideoBean;
import com.wd.health.presenter.CollectVideoPresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/25 09:42
 */
public class VideoFragment extends WDFragment {

    XRecyclerView recycler;


    LinearLayout Null;
    private CollectVideoPresenter collectVideoPresenter;
    private CollectVideoAdapter collectVideoAdapter;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        recycler=getView().findViewById(R.id.xrecycler);
        Null=getView().findViewById(R.id.Null);
        collectVideoPresenter = new CollectVideoPresenter(new UnVideo());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        final PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);
    }

    private int page=1;
    @Override
    public void onResume() {
        super.onResume();
        List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()<=0){
            intentByRouter("/LoginActivity/");

        }else {
             LoginBean user = loginBeans.get(0);
            collectVideoAdapter = new CollectVideoAdapter(getContext());
            recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page=1;
                    collectVideoPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    collectVideoPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }
            });
            recycler.setAdapter(collectVideoAdapter);
            recycler.refresh();
        }
    }

    public class UnVideo implements DataCall<Result<List<CollectVideoBean>>>{
        @Override
        public void success(Result<List<CollectVideoBean>> data, Object... args) {
            final List<CollectVideoBean> result = data.getResult();
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
                Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
