package com.wd.health.frag;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.health.bean.CircleFindDepartmentBean;
import com.bw.health.bean.CircleListBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.appbar.AppBarLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.adapter.CircleFindDepartmentAdapter;
import com.wd.health.adapter.CircleListAdapter;
import com.wd.health.presenter.CircleFindDepartmentPresenter;
import com.wd.health.presenter.CircleListPresenter;
import com.wd.health.utils.ViewUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class CircleFrag extends WDFragment {
    TextView mTextView;
    private RelativeLayout mFLayout;
    private RecyclerView rc1;
    private CircleFindDepartmentAdapter circleFindDepartmentAdapter;
    private XRecyclerView rc2;
    private CircleListPresenter circleListPresenter;
    private CircleListAdapter circleListAdapter;
    private TextView circle_frag_keshi;
    public int page = 1;
    private int id_s;

    @Override
    public String getPageName() {
        return "病友圈";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_frag_layout;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void initView() {
        ViewUtils.setImmersionStateMode((AppCompatActivity) getActivity());

        AppBarLayout mAppBarLayout = getView().findViewById(R.id.appbar);
        View linear_layout = getView().findViewById(R.id.circle_linearlayout1);
        mTextView = getView().findViewById(R.id.tv_info);
        mFLayout = getView().findViewById(R.id.fl_layout);
        final LinearLayout linearLayout = getView().findViewById(R.id.edit_dd);

        //头像
        SimpleDraweeView image_headpic = getView().findViewById(R.id.circle_image_head);
        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(getActivity(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        String headPic = loginBeanDao.loadAll().get(0).getHeadPic();
        image_headpic.setImageURI(headPic);
        image_headpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter("/MineActivity/");

            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                if (percent == 1.0) {
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }

                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
                if (dy <= toolbarHeight) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    //mFLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    //mFLayout.setBackgroundColor(Color.RED);
                    // mTextView.setText("setBackgroundColor(Color.argb((int) " + (int) alpha + ", 255, 255, 255))" + "mFLayout.setAlpha(" + percent + ")");
                }

                //第二种

                // mFLayout.setAlpha(percent);

            }
        });
        //-----科室列表----------------------------------


        circle_frag_keshi = getView().findViewById(R.id.circle_frag_keshi);
        //关联presenter
        CircleFindDepartmentPresenter presenter1 = new CircleFindDepartmentPresenter(new CircleFindDepartmentCall());
        presenter1.reqeust();


        rc1 = getView().findViewById(R.id.circlr_frag_rc1);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc1.setLayoutManager(linearLayoutManager);
        //适配器
        circleFindDepartmentAdapter = new CircleFindDepartmentAdapter(getActivity());
        rc1.setAdapter(circleFindDepartmentAdapter);
        circleFindDepartmentAdapter.setCall(new CircleFindDepartmentAdapter.Call() {

            @Override
            public void showCall(int id, String name) {
                id_s = id;
                circleListPresenter.reqeust(String.valueOf(id), page + "", "5");
                circle_frag_keshi.setText(name);
            }
        });

        //-----科室列表----------------------------------

        //-----------根据关键字搜索病友圈-----------------------
        ImageView circle_search = getView().findViewById(R.id.circle_frag_search_image);
        //跳转根据关键字搜索病友圈
        circle_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter("/FindSickCircleInfoActivity/");
            }
        });


        //----------根据关键字搜索病友圈------尾巴------------------

        //-----病友圈列表展示----------------------------------
        rc2 = getView().findViewById(R.id.circlr_frag_rc2);
        //关联presenter
        circleListPresenter = new CircleListPresenter(new CircleListCall());
        circleListPresenter.reqeust("7", page + "", "5");
        //布局管理器
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rc2.setLayoutManager(linearLayoutManager1);
        //适配器
        circleListAdapter = new CircleListAdapter(getActivity());
        rc2.setAdapter(circleListAdapter);

        rc2.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                circleListPresenter.reqeust(String.valueOf(id_s), page + "", "5");

            }

            @Override
            public void onLoadMore() {
                page++;
                circleListPresenter.reqeust(String.valueOf(id_s), "" + page++, "5");


            }
        });

        //-----病友圈列表展示----------------------------------
    }

    //-----科室列表----------成功--失败---的方法------------------------
    class CircleFindDepartmentCall implements DataCall<Result<List<CircleFindDepartmentBean>>> {


        @Override
        public void success(Result<List<CircleFindDepartmentBean>> data, Object... args) {
            List<CircleFindDepartmentBean> result = data.getResult();
            circleFindDepartmentAdapter.getData(result);

            circleFindDepartmentAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //-----科室列表----------成功--失败---的方法------------------------

    //-----病友圈列表展示------成功--失败---的方法----------------------------
    class CircleListCall implements DataCall<Result<List<CircleListBean>>> {

        @Override
        public void success(Result<List<CircleListBean>> data, Object... args) {

            List<CircleListBean> result = data.getResult();
            rc2.loadMoreComplete();
            rc2.refreshComplete();
            if (page == 1) {
                circleListAdapter.getDatt(result);
            }
            circleListAdapter.getDatt(result);
            circleListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    //-----病友圈列表展示-----------成功--失败---的方法-----------------------


}
