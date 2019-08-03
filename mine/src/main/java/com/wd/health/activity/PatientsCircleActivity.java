package com.wd.health.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.utils.L;
import com.wd.health.R;
import com.wd.health.adapter.ShowCircleAdapter;
import com.wd.health.bean.PatientsCircleBean;
import com.wd.health.presenter.PatientsCirclePresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/30
 * @Description: 我的病友圈
 */
@Route(path = "/PatientsCircleActivity/")
public class PatientsCircleActivity extends WDActivity {

    private PatientsCirclePresenter patientsCirclePresenter;
    private ImageView back_iamge;
    private XRecyclerView rc1;
    private Long id_user;
    private String sessionId;
    private int page = 1;
    private ShowCircleAdapter showCircleAdapter;
    private RelativeLayout no_magess_layout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patients_circle;
    }

    @Override
    protected void initView() {
        //返回首页
        back_iamge = findViewById(R.id.my_circle_home_back);
        rc1 = findViewById(R.id.my_circle_home_rc1);
        no_magess_layout = findViewById(R.id.layout_wu_magess);
        Button send_circle = findViewById(R.id.send_circle_button);


        //返回
        back_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //去发布病友圈
        send_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter("/CircleWritActivity/");
            }
        });


        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(PatientsCircleActivity.this, LoginBeanDao.TABLENAME).getLoginBeanDao();
        id_user = loginBeanDao.loadAll().get(0).getId();
        sessionId = loginBeanDao.loadAll().get(0).getSessionId();
        //关联presenter
        patientsCirclePresenter = new PatientsCirclePresenter(new ShowMyCircleCall());
        patientsCirclePresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page), "5");

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PatientsCircleActivity.this);
        rc1.setLayoutManager(linearLayoutManager);
        //适配器
        showCircleAdapter = new ShowCircleAdapter(this);
        rc1.setAdapter(showCircleAdapter);
        showCircleAdapter.setCall(new ShowCircleAdapter.Call() {
            @Override
            public void ShowCall() {
                //intentByRouter("/CircleCommentActivity/");
               // finish();
            }
        });

        //上拉刷新 下拉加载
        rc1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                patientsCirclePresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page), "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                patientsCirclePresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page++), "5");

            }
        });


    }

    class ShowMyCircleCall implements DataCall<Result<List<PatientsCircleBean>>> {

        @Override
        public void success(Result<List<PatientsCircleBean>> data, Object... args) {
            List<PatientsCircleBean> result_mycircle = data.getResult();
            if (result_mycircle.size() == 0 && page == 1) {
                no_magess_layout.setVisibility(View.VISIBLE);
                rc1.setVisibility(View.GONE);

            } else {
                rc1.setVisibility(View.VISIBLE);
                no_magess_layout.setVisibility(View.GONE);

            }


            rc1.loadMoreComplete();
            rc1.refreshComplete();
            if (page == 1) {
                showCircleAdapter.clear();
            }

            showCircleAdapter.setData(result_mycircle);
            showCircleAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void destoryData() {


    }

}
