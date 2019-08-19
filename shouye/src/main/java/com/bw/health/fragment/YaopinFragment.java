package com.bw.health.fragment;

import android.annotation.SuppressLint;

import com.bw.health.shouye.R;
import com.bw.health.shouye.R2;
import com.bw.health.adapter.yaopin.YpOneAdapter;
import com.bw.health.adapter.yaopin.YpTwoAdapter;
import com.bw.health.bean.Result;
import com.bw.health.bean.YaoPinBean;
import com.bw.health.bean.YaoPinTwoBean;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.YpOnePresenter;
import com.bw.health.presenter.YpTwoPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * com.bw.health.fragment
 *
 * @author 李宁康
 * @date 2019 2019/07/14 17:15
 */
public class YaopinFragment extends WDFragment {
    @BindView(R2.id.recyclerOne)
    RecyclerView recyclerOne;
    @BindView(R2.id.recyclerTwo)
    XRecyclerView recyclerTwo;
    private YpOnePresenter ypOnePresenter;
    private YpOneAdapter ypOneAdapter;
    private YpTwoPresenter ypTwoPresenter;
    private YpTwoAdapter ypTwoAdapter;
    private long id = 1;
    private int page = 1;

    @Override
    public String getPageName() {
        return "常用药品";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.yaopin_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {

        recyclerOne.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ypOnePresenter = new YpOnePresenter(new YpOne());
        ypOnePresenter.reqeust();
        ypOneAdapter = new YpOneAdapter(getContext());
        recyclerOne.setAdapter(ypOneAdapter);

        //药品
        recyclerTwo.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ypTwoPresenter = new YpTwoPresenter(new YpTwo());

        recyclerTwo.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                ypTwoPresenter.reqeust(id, page, 12);
            }

            @Override
            public void onLoadMore() {
                page++;
                ypTwoPresenter.reqeust(id, page, 12);
            }
        });
        ypTwoAdapter = new YpTwoAdapter(getContext());
        recyclerTwo.setAdapter(ypTwoAdapter);
        recyclerTwo.refresh();

        ypOneAdapter.setCall(new YpOneAdapter.Call() {
            @Override
            public void dataCall(YaoPinBean yaoPinBean) {
                id=yaoPinBean.id;
                recyclerTwo.refresh();
            }
        });
    }

    public class YpTwo implements DataCall<Result<List<YaoPinTwoBean>>> {
        @Override
        public void success(Result<List<YaoPinTwoBean>> data, Object... args) {
            recyclerTwo.loadMoreComplete();
            recyclerTwo.refreshComplete();
            if (page==1){
                ypTwoAdapter.clear();
            }
            ypTwoAdapter.setList(data.getResult());
            ypTwoAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    public class YpOne implements DataCall<Result<List<YaoPinBean>>> {

        @Override
        public void success(Result<List<YaoPinBean>> data, Object... args) {
            ypOneAdapter.clear();
            ypOneAdapter.setList(data.getResult());
            ypOneAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
