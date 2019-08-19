package com.wd.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
import com.bw.health.util.GetDaoUtil;
import com.google.android.material.snackbar.Snackbar;
import com.wd.health.mine.R;
import com.wd.health.bean.UserArchivesBean;
import com.wd.health.presenter.DeleteArchivesPresenter;
import com.wd.health.presenter.UserArchivesPresenter;
import com.wd.health.view.NineGridTestLayout;

import java.util.Arrays;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

public class MyFileActivity extends WDActivity implements View.OnClickListener {

    private UserArchivesPresenter userArchivesPresenter;
    private ImageView mBack;
    /**
     * 病症
     */
    private TextView mZhengzhuang;
    /**
     * 现病史
     */
    private TextView mBingshi;
    /**
     * 既往病史
     */
    private TextView mJbingshi;
    /**
     * 医院
     */
    private TextView mYiyuan;
    /**
     * 时间
     */
    private TextView mTime;
    /**
     * 过程
     */
    private TextView mGuocheng;
    private NineGridTestLayout mLayoutNineGrid;
    /**
     * 添加
     */
    private TextView mAdd;
    private LinearLayout mNull;
    private NestedScrollView file;
    TextView delete;
    TextView bianji;
    private DeleteArchivesPresenter deleteArchivesPresenter;
    RelativeLayout layout_main;
    /**
     * 取消
     */
    private TextView quxiao;
    /**
     * 确认
     */
    private TextView queren;
    private RelativeLayout dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_file;
    }

    @Override
    protected void initView() {
        userArchivesPresenter = new UserArchivesPresenter(new UserArchives());
        bianji=findViewById(R.id.bianji);
        layout_main = findViewById(R.id.layout_main);
        mBack = (ImageView) findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        mBack.setOnClickListener(this);
        quxiao = (TextView) findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
        queren = (TextView) findViewById(R.id.queren);
        queren.setOnClickListener(this);
        dialog = (RelativeLayout) findViewById(R.id.dialog);
        file = findViewById(R.id.file);
        mZhengzhuang = (TextView) findViewById(R.id.zhengzhuang);
        mBingshi = (TextView) findViewById(R.id.bingshi);
        mJbingshi = (TextView) findViewById(R.id.jbingshi);
        mYiyuan = (TextView) findViewById(R.id.yiyuan);
        mTime = (TextView) findViewById(R.id.time);
        mGuocheng = (TextView) findViewById(R.id.guocheng);
        mLayoutNineGrid = (NineGridTestLayout) findViewById(R.id.layout_nine_grid);
        mLayoutNineGrid.setOnClickListener(this);
        mAdd = (TextView) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mNull = (LinearLayout) findViewById(R.id.Null);
        deleteArchivesPresenter = new DeleteArchivesPresenter(new DeleteArchives());
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MyFileActivity.this, AddFileActivity.class);
                startActivity(intent);
            }
        });
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyFileActivity.this,UpdateFileActivity.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setVisibility(View.VISIBLE);
                queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
                        deleteArchivesPresenter.reqeust(loginBean.getId(), loginBean.getSessionId(), id);
                        dialog.setVisibility(View.GONE);
                    }
                });
               quxiao.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.setVisibility(View.GONE);
                   }
               });
            }
        });

    }


    public class DeleteArchives implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {
            LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
            Snackbar.make(layout_main, data.getMessage(), Snackbar.LENGTH_LONG).show();

            userArchivesPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Snackbar.make(layout_main, data.getDisplayMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size() <= 0) {
            intentByRouter("/LoginActivity/");

        } else {
            final LoginBean loginBean = loginBeans.get(0);
            userArchivesPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());
        }
    }

    @Override
    protected void destoryData() {

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.layout_nine_grid) {
        } else if (i == R.id.add) {
        } else {
        }
    }

    long id = 0;

    public class UserArchives implements DataCall<Result<UserArchivesBean>> {
        @Override
        public void success(Result<UserArchivesBean> data, Object... args) {
            UserArchivesBean result = data.getResult();
            if (null != result) {
                id = result.id;
                file.setVisibility(View.VISIBLE);
                mNull.setVisibility(View.GONE);
                mZhengzhuang.setText(result.diseaseMain);
                mBingshi.setText(result.diseaseNow);
                mJbingshi.setText(result.diseaseBefore);
                mYiyuan.setText(result.treatmentHospitalRecent);
                mGuocheng.setText(result.treatmentProcess);
                long treatmentStartTime = result.treatmentStartTime;
                long treatmentEndTime = result.treatmentEndTime;
                String start = DateUtils.timeStamp2Date(treatmentStartTime, "yyyy.MM.dd");
                String end = DateUtils.timeStamp2Date(treatmentEndTime, "yyyy.MM.dd");
                String startSubstring = start.substring(5, start.length());
                String endSubstring = start.substring(5, end.length());
                if (startSubstring.equals(endSubstring)) {
                    mTime.setText(start + "—" + endSubstring);
                } else {
                    mTime.setText(start + "—" + end);
                }

                String[] split = result.picture.split(",");
                if (split.length > 0) {
                    List<String> urls = Arrays.asList(split);
                    mLayoutNineGrid.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                    mLayoutNineGrid.setSpacing(5); //动态设置图片之间的间隔
                    mLayoutNineGrid.setUrlList(urls); //最后再设置图片url
                }

            } else {
                file.setVisibility(View.GONE);
                mNull.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
