package com.wd.health.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.adapter.GuanzhuAdapter;
import com.wd.health.bean.DoctorBean;
import com.wd.health.presenter.CancelFollowPresenter;
import com.wd.health.presenter.FindUserDoctorFollowListPresenter;

import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/GuanzhuActivity/")
public class GuanzhuActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.recy)
    RecyclerView recy;
    @BindView(R2.id.zanwu)
    LinearLayout zanwu;
    private FindUserDoctorFollowListPresenter presenter;
    private CancelFollowPresenter cancelFollowPresenter;
    private List<LoginBean> list1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guanzhu;
    }

    @Override
    protected void initView() {
        list1 = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        presenter = new FindUserDoctorFollowListPresenter(new FindUserDoctorFollowList());
        cancelFollowPresenter = new CancelFollowPresenter(new CancelFollow());
        if (list1 !=null&& list1.size()>0){
            recy.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            presenter.reqeust(list1.get(0).getId().intValue(), list1.get(0).getSessionId()+"",1,10);

        }

    }

    @Override
    protected void destoryData() {

    }


    @OnClick(R2.id.back)
    public void onViewClicked() {
        finish();
    }
    public class FindUserDoctorFollowList implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result<List<DoctorBean>> result= (Result<List<DoctorBean>>) data;
            List<DoctorBean> list = result.getResult();
            if (list.size()>0&&list!=null){
                GuanzhuAdapter adapter = new GuanzhuAdapter(R.layout.guanzhuitem, list);
                recy.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            cancelFollowPresenter.reqeust(list1.get(0).getId().intValue(),list1.get(0).getSessionId()+"",list.get(position).getDoctorId());
                            list.remove(position);
                            if (list.size()>0&&list!=null){
                                adapter.notifyDataSetChanged();
                            }else {
                                recy.setVisibility(View.GONE);
                                zanwu.setVisibility(View.VISIBLE);
                            }
                    }
                });
            }else {
                recy.setVisibility(View.GONE);
                zanwu.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class CancelFollow implements DataCall{

        @Override
        public void success(Object data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
