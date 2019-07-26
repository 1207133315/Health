package com.wd.health.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.adapter.CollectCircleAdapter;
import com.wd.health.adapter.CollectVideoAdapter;
import com.wd.health.bean.CollectCircleBean;
import com.wd.health.presenter.CollectCirclePresenter;
import com.wd.health.presenter.CollectVideoPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/25 09:42
 */
public class CircleFragment extends WDFragment {
    XRecyclerView recycler;


    LinearLayout Null;
    private CollectCirclePresenter collectCirclePresenter;
    private CollectCircleAdapter collectCircleAdapter;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        recycler=getView().findViewById(R.id.xrecycler);
        Null=getView().findViewById(R.id.Null);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        collectCirclePresenter = new CollectCirclePresenter(new CollectCircle());
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
            collectCircleAdapter = new CollectCircleAdapter(getContext());
            recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page=1;
                    collectCirclePresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    collectCirclePresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }
            });
            recycler.setAdapter(collectCircleAdapter);
            recycler.refresh();
        }
    }

    public class CollectCircle implements DataCall<Result<List<CollectCircleBean>>>{
        @Override
        public void success(Result<List<CollectCircleBean>> data, Object... args) {
            final List<CollectCircleBean> result = data.getResult();
            if (page==1&&result.size()<=0){
                Null.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            }else {
                Null.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
            }
            if (page==1){
                collectCircleAdapter.clear();
            }
            recycler.loadMoreComplete();
            recycler.refreshComplete();
            collectCircleAdapter.setList(data.getResult());
            collectCircleAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
