package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
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
import com.wd.health.mine.R;
import com.wd.health.adapter.BeAdoptionAdapter;
import com.wd.health.bean.BeAdoptedBean;
import com.wd.health.presenter.BeAdoptedPresenter;

import java.util.List;

/**
 * @Auther: 郭亚杰
 * @Date:2019/8/1
 * @Description: 查询我的被采纳的建议
 */
@Route(path = "/BeAdoptedActivity/")
public class BeAdoptedActivity extends WDActivity {

    private ImageView be_adopted_back;
    private XRecyclerView be_adopted_rc1;
    private BeAdoptedPresenter beAdoptedPresenter;
    private Long id_user;
    private String sessionId;
    private int page = 1;
    private BeAdoptionAdapter beAdoptionAdapter;
    private RelativeLayout be_wu_meagess;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_be_adopted;
    }

    @Override
    protected void initView() {

        be_adopted_back = findViewById(R.id.be_adopted_back);
        be_adopted_rc1 = findViewById(R.id.be_adopted_rc1);//查询我的被采纳的建议展示列表
        be_wu_meagess = findViewById(R.id.layout_wu_magess);

        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(this, LoginBeanDao.TABLENAME).getLoginBeanDao();
        id_user = loginBeanDao.loadAll().get(0).getId();
        sessionId = loginBeanDao.loadAll().get(0).getSessionId();

        //返回
        be_adopted_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //---------------------------查询我的被采纳的建议-----------------------
        //关联P
        beAdoptedPresenter = new BeAdoptedPresenter(new BeAdoptedCall());
        beAdoptedPresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page), "5");
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        be_adopted_rc1.setLayoutManager(linearLayoutManager);
        //适配器
        beAdoptionAdapter = new BeAdoptionAdapter(this);
        be_adopted_rc1.setAdapter(beAdoptionAdapter);
        be_adopted_rc1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                beAdoptedPresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page), "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                beAdoptedPresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(page++), "5");

            }
        });

        //---------------------------查询我的被采纳的建议-------尾巴----------------

    }

    //---------------------------查询我的被采纳的建议-----成功失败------------------
    class BeAdoptedCall implements DataCall<Result<List<BeAdoptedBean>>> {

        @Override
        public void success(Result<List<BeAdoptedBean>> data, Object... args) {
            List<BeAdoptedBean> result_be = data.getResult();
            if (result_be.size() == 0 && page == 1) {
                be_wu_meagess.setVisibility(View.VISIBLE);
                be_adopted_rc1.setVisibility(View.GONE);
            } else {
                be_wu_meagess.setVisibility(View.GONE);
                be_adopted_rc1.setVisibility(View.VISIBLE);

            }

            be_adopted_rc1.refreshComplete();
            be_adopted_rc1.loadMoreComplete();
            if (page == 1) {
                beAdoptionAdapter.clear();
            }


            beAdoptionAdapter.setData(result_be);
            beAdoptionAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //---------------------------查询我的被采纳的建议-----成功失败--尾巴----------------


    @Override
    protected void destoryData() {

    }
}
