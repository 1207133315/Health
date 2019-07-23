package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.adapter.CircleUserInfoAdapter;
import com.wd.health.bean.CircleUserInfoBean;
import com.wd.health.presenter.CircleUserInfoPresenter;

import java.util.List;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/22
 * @Description: 用户信息页
 */
@Route(path = "/CircleUserInfoActivity/")
public class CircleUserInfoActivity extends AppCompatActivity {

    private CircleUserInfoPresenter circleUserInfoPresenter;
    public int page = 1;
    public int counts = 10;
    private CircleUserInfoAdapter circleUserInfoAdapter;
    private XRecyclerView userinfo_rc1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_user_info);
        //获取传递过来的值
        Intent intent = getIntent();
        String commentUserId = intent.getStringExtra("commentUserId");
        String headPic = intent.getStringExtra("headPic");
        String nickName = intent.getStringExtra("nickName");

        //--------------用户信息页---病友发布的病友圈--------------------
        //找控件
        SimpleDraweeView head_image = findViewById(R.id.circle_userinfo_head_image);
        TextView nameText = findViewById(R.id.circle_userinfo_text);
        userinfo_rc1 = findViewById(R.id.circle_userinfo_rc1);

        //赋值
        head_image.setImageURI(headPic);
        nameText.setText(nickName);


        //关联病友发布的病友圈presenter
        circleUserInfoPresenter = new CircleUserInfoPresenter(new CircleUserInfoCall());
        circleUserInfoPresenter.reqeust(commentUserId, page + "", "5");

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userinfo_rc1.setLayoutManager(linearLayoutManager);
        //适配器
        circleUserInfoAdapter = new CircleUserInfoAdapter(this);
        userinfo_rc1.setAdapter(circleUserInfoAdapter);


        //上拉加载  下拉刷新
        userinfo_rc1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                circleUserInfoPresenter.reqeust(commentUserId, page + "", "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                circleUserInfoPresenter.reqeust(commentUserId, "" + page++, "5");

            }
        });

        //--------------用户信息页---病友发布的病友圈-----尾巴---------------
    }

    //--------------用户信息页---病友发布的病友圈--成功失败的方法------------------

    class CircleUserInfoCall implements DataCall<Result<List<CircleUserInfoBean>>> {

        @Override
        public void success(Result<List<CircleUserInfoBean>> data, Object... args) {
            List<CircleUserInfoBean> userinfo_result = data.getResult();
            userinfo_rc1.loadMoreComplete();
            userinfo_rc1.refreshComplete();
            if (page == 1) {
                circleUserInfoAdapter.clear();
            }

            circleUserInfoAdapter.setData(userinfo_result);
            circleUserInfoAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    //--------------用户信息页---病友发布的病友圈--成功失败的方法---尾巴--------------


}
