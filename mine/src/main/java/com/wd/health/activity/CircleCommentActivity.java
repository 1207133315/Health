package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.adapter.CircleCommentAdapter;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.OutsideBean;
import com.wd.health.presenter.CircleCommentPresenter;

import java.util.List;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/30
 * @Description: 我的病友圈评论列表
 */
@Route(path = "/CircleCommentActivity/")
public class CircleCommentActivity extends WDActivity {


    private ImageView back_iamge;
    private XRecyclerView rc1;
    private RelativeLayout no_magess_layout;
    private CircleCommentPresenter circleCommentPresenter;
    private int page = 1;
    private CircleCommentAdapter circleCommentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_comment;
    }

    @Override
    protected void initView() {
        back_iamge = findViewById(R.id.circle_comment_back);
        rc1 = findViewById(R.id.circle_comment_rc1);
        no_magess_layout = findViewById(R.id.layout_wu_magess);

        Intent intent = getIntent();
        String id_sick = intent.getStringExtra("id");
        OutsideBean.id_outside = id_sick;

        //返回
        back_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //关联p
        circleCommentPresenter = new CircleCommentPresenter(new CircleCommentCall());
        circleCommentPresenter.reqeust(id_sick, String.valueOf(page), "5");
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CircleCommentActivity.this);
        rc1.setLayoutManager(linearLayoutManager);
        //适配器
        circleCommentAdapter = new CircleCommentAdapter(CircleCommentActivity.this);
        rc1.setAdapter(circleCommentAdapter);


        rc1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                circleCommentPresenter.reqeust(id_sick, String.valueOf(page), "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                circleCommentPresenter.reqeust(id_sick, String.valueOf(page++), "5");

            }
        });
    }


    class CircleCommentCall implements DataCall<Result<List<CircleCommentListBean>>> {

        @Override
        public void success(Result<List<CircleCommentListBean>> data, Object... args) {
            List<CircleCommentListBean> result_comment = data.getResult();

            if (result_comment.size() == 0 && page == 1) {
                no_magess_layout.setVisibility(View.VISIBLE);
                rc1.setVisibility(View.GONE);
            } else {
                rc1.setVisibility(View.VISIBLE);
                no_magess_layout.setVisibility(View.GONE);
            }


            rc1.loadMoreComplete();
            rc1.refreshComplete();
            if (page == 1) {
                circleCommentAdapter.clear();
            }


            circleCommentAdapter.setData(result_comment);
            circleCommentAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void destoryData() {

    }

}
