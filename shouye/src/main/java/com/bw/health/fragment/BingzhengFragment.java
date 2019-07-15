package com.bw.health.fragment;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.adapter.bingzheng.BzOneAdapter;
import com.bw.health.adapter.bingzheng.BzTwoAdapter;
import com.bw.health.bean.BingZhengBean;
import com.bw.health.bean.DepartmentBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.BingZhengPresenter;
import com.bw.health.presenter.DepartmentPresenter;

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
public class BingzhengFragment extends WDFragment {
    @BindView(R2.id.recyclerOne)
    RecyclerView recyclerOne;
    @BindView(R2.id.recyclerTwo)
    RecyclerView recyclerTwo;
    private DepartmentPresenter departmentPresenter;
    private BzOneAdapter bzOneAdapter;
    private BingZhengPresenter bingZhengPresenter;
    private BzTwoAdapter bzTwoAdapter;

    @Override
    public String getPageName() {
        return "常见病症";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bingzheng_frag_layout;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        /*
        常见病症
        * */
        //科室
        departmentPresenter = new DepartmentPresenter(new Department());
        departmentPresenter.reqeust();
        recyclerOne.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        bzOneAdapter = new BzOneAdapter(getContext());
        recyclerOne.setAdapter(bzOneAdapter);

        //病症
        bingZhengPresenter = new BingZhengPresenter(new Bingzheng());
        bzTwoAdapter = new BzTwoAdapter(getContext());
        bingZhengPresenter.reqeust((long)7);
        recyclerTwo.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerTwo.setAdapter(bzTwoAdapter);
        bzOneAdapter.setCall(new BzOneAdapter.Call() {
            @Override
            public void dataCall(DepartmentBean departmentBean) {
                bingZhengPresenter.reqeust(departmentBean.id);
            }
        });
    }

    public class Bingzheng implements DataCall<Result<List<BingZhengBean>>>{
        @Override
        public void success(Result<List<BingZhengBean>> data, Object... args) {
            bzTwoAdapter.clecar();
            bzTwoAdapter.addList(data.getResult());
            bzTwoAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class Department implements DataCall<Result<List<DepartmentBean>>> {
        @Override
        public void success(Result<List<DepartmentBean>> data, Object... args) {
            bzOneAdapter.setList(data.getResult());
            bzOneAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage()+data.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
