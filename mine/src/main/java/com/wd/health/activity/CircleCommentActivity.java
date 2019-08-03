package com.wd.health.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.adapter.CircleCommentAdapter;
import com.wd.health.adapter.CircleElseCommentAdapter;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.MySickCircleCommentListBean;
import com.wd.health.bean.OutsideBean;
import com.wd.health.presenter.CircleCommentPresenter;
import com.wd.health.presenter.MySickCircleCommentListPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private MySickCircleCommentListPresenter mySickCircleCommentListPresenter;
    private Long id_user;
    private String sessionId;
    private SimpleDraweeView adoption_iamge;
    private ImageView adoption_renzheng;
    private ImageView adoption_biaoshi;
    private TextView adoption_name;
    private TextView adoption_comment;
    private TextView adoption_time;
    private CheckBox adoption_goods1;
    private TextView adoption_goods1_num;
    private CheckBox adoption_goods2;
    private TextView adoption_goods2_num;
    private XRecyclerView circle_else_rc2;
    private LinearLayout adoption_layout;
    private CircleElseCommentAdapter circleElseCommentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_comment;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String id_sick = intent.getStringExtra("id");
        OutsideBean.id_outside = id_sick;


        back_iamge = findViewById(R.id.circle_comment_back);
        rc1 = findViewById(R.id.circle_comment_rc1);//采纳建议展示列表
        no_magess_layout = findViewById(R.id.layout_wu_magess);//无信息展示列表

        //-------------评论列表已采纳页----
        adoption_iamge = findViewById(R.id.my_circle_caina_doctor_image);
        adoption_renzheng = findViewById(R.id.my_circle_caina_doctor_renzheng);
        adoption_biaoshi = findViewById(R.id.my_circle_caina_doctor_biaoshi);

        adoption_name = findViewById(R.id.my_circle_caina_name);
        adoption_comment = findViewById(R.id.my_circle_caina_comment);
        adoption_time = findViewById(R.id.my_circle_caina_time);

        adoption_goods1 = findViewById(R.id.my_circle_caina_goods1);
        adoption_goods1_num = findViewById(R.id.my_circle_caina_goods1_num);
        adoption_goods2 = findViewById(R.id.my_circle_caina_goods2);
        adoption_goods2_num = findViewById(R.id.my_circle_caina_goods2_num);

        circle_else_rc2 = findViewById(R.id.circle_else_rc2);
        adoption_layout = findViewById(R.id.my_circle_adoption_layout);//已采纳列表展示


        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(this, LoginBeanDao.TABLENAME).getLoginBeanDao();
        id_user = loginBeanDao.loadAll().get(0).getId();
        sessionId = loginBeanDao.loadAll().get(0).getSessionId();


        //返回
        back_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //-------------评论列表采纳页----------------------------------------
        //关联p
        circleCommentPresenter = new CircleCommentPresenter(new CircleCommentCall());
        circleCommentPresenter.reqeust(id_sick, String.valueOf(page), "5");
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CircleCommentActivity.this);
        rc1.setLayoutManager(linearLayoutManager);
        //适配器
        circleCommentAdapter = new CircleCommentAdapter(CircleCommentActivity.this);
        rc1.setAdapter(circleCommentAdapter);
        // rc1.setVisibility(View.GONE);

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
        //-------------评论列表采纳页-------尾巴---------------------------------

        //-------------评论列表已采纳页----------------------------------------

        mySickCircleCommentListPresenter = new MySickCircleCommentListPresenter(new OneCircleCommentCall());
        mySickCircleCommentListPresenter.reqeust(String.valueOf(id_user), sessionId, id_sick, String.valueOf(page), "5");
        //布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(CircleCommentActivity.this);
        circle_else_rc2.setLayoutManager(linearLayoutManager1);
        //适配器
        circleElseCommentAdapter = new CircleElseCommentAdapter(this);
        circle_else_rc2.setAdapter(circleElseCommentAdapter);
        circle_else_rc2.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mySickCircleCommentListPresenter.reqeust(String.valueOf(id_user), sessionId, id_sick, String.valueOf(page), "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                mySickCircleCommentListPresenter.reqeust(String.valueOf(id_user), sessionId, id_sick, String.valueOf(page++), "5");

            }
        });

        //-------------评论列表已采纳页-------尾巴---------------------------------


    }


    //-------------评论列表采纳页-----成功--失败-----------------------------------

    class CircleCommentCall implements DataCall<Result<List<CircleCommentListBean>>> {

        @Override
        public void success(Result<List<CircleCommentListBean>> data, Object... args) {
            List<CircleCommentListBean> result_comment = data.getResult();

            if (result_comment.size() == 0 && page == 1) {
                no_magess_layout.setVisibility(View.VISIBLE);
                // rc1.setVisibility(View.GONE);
            } else {
                // rc1.setVisibility(View.VISIBLE);
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
    //-------------评论列表采纳页-----成功--失败--尾巴---------------------------------

    //-------------评论列表已采纳页----成功--失败------------------------------------
    class OneCircleCommentCall implements DataCall<Result<MySickCircleCommentListBean>> {

        @Override
        public void success(Result<MySickCircleCommentListBean> data, Object... args) {
            MySickCircleCommentListBean result = data.getResult();
            MySickCircleCommentListBean.AdoptionSickCircleCommentBean adoptionBean = result.getAdoptionSickCircleComment();
            List<MySickCircleCommentListBean.OtherSickCircleCommentListBean> otherBean = result.getOtherSickCircleCommentList();

            if (adoptionBean == null) {
                rc1.setVisibility(View.VISIBLE);
                adoption_layout.setVisibility(View.GONE);

            } else {
                adoption_layout.setVisibility(View.VISIBLE);
                rc1.setVisibility(View.GONE);
            }


            String headPic = adoptionBean.getHeadPic();
            String nickName = adoptionBean.getNickName();
            String content = adoptionBean.getContent();
            long commentTime = adoptionBean.getCommentTime();
            int supportNum = adoptionBean.getSupportNum();
            int opposeNum = adoptionBean.getOpposeNum();
            int whetherDoctor = adoptionBean.getWhetherDoctor();


            adoption_iamge.setImageURI(headPic);
            adoption_name.setText(nickName);
            adoption_comment.setText(content);
            adoption_goods1_num.setText(supportNum + "");
            adoption_goods2_num.setText(opposeNum + "");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String time = simpleDateFormat.format(new Date(commentTime));
            adoption_time.setText(time + "");

            //判断是否是医生
            if (whetherDoctor == 1) {
                adoption_biaoshi.setVisibility(View.VISIBLE);
                adoption_renzheng.setVisibility(View.VISIBLE);

            } else {
                adoption_biaoshi.setVisibility(View.GONE);
                adoption_renzheng.setVisibility(View.GONE);
            }

            circle_else_rc2.loadMoreComplete();
            circle_else_rc2.refreshComplete();
            if (page==1){
                circleElseCommentAdapter.clear();
            }



            circleElseCommentAdapter.setDatt(otherBean);
            circleElseCommentAdapter.notifyDataSetChanged();


        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //-------------评论列表已采纳页----成功--失败---尾巴---------------------------------


    @Override
    protected void destoryData() {

    }

}
