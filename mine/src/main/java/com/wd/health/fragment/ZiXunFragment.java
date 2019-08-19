package com.wd.health.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.adapter.MationListAdapter;
import com.wd.health.presenter.CollectZiXuPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/25 09:42
 */
public class ZiXunFragment extends WDFragment {
    @BindView(R2.id.recycler)
    XRecyclerView recycler;
    @BindView(R2.id.Null)
    LinearLayout Null;
    private CollectZiXuPresenter collectZiXuPresenter;
    private MationListAdapter mationListAdapter;
    private int page;
    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.zixun_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        collectZiXuPresenter = new CollectZiXuPresenter(new ZiXun());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


    }

    @Override
    public void onResume() {
        super.onResume();
         List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()<=0){
            intentByRouter("/LoginActivity/");

        }else {
            final LoginBean user = loginBeans.get(0);
            mationListAdapter = new MationListAdapter(getContext());
            recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page=1;
                    collectZiXuPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    collectZiXuPresenter.reqeust(user.getId(),user.getSessionId(),page,10);
                }
            });
            recycler.setAdapter(mationListAdapter);
            recycler.refresh();
        }

    }

    public class ZiXun implements DataCall<Result<List<MationBean>>> {
        @Override
        public void success(Result<List<MationBean>> data, Object... args) {
            final List<MationBean> result = data.getResult();
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).thumbnail .length()>0) {
                    String[] split = result.get(i).thumbnail.split(";");

                    Log.i("lnk", "success: "+split.length);

                    if (split.length == 1) {
                        result.get(i).aa = 1;
                    }
                    if (split.length == 3) {
                        result.get(i).aa=2;
                    }
                }else {
                    result.get(i).aa=3;
                }

            }

                if (page==1&&result.size()<=0){
                    Null.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }else {
                    Null.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                }
                if (page==1){
                    mationListAdapter.clear();
                }
                recycler.loadMoreComplete();
                recycler.refreshComplete();
                mationListAdapter.addList(data.getResult());
                mationListAdapter.notifyDataSetChanged();
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
