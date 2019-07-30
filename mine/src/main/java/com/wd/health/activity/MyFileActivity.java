package com.wd.health.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.bean.UserArchivesBean;
import com.wd.health.presenter.UserArchivesPresenter;
import com.wd.health.view.NineGridTestLayout;

import java.util.Arrays;
import java.util.List;

public class MyFileActivity extends WDActivity implements View.OnClickListener {

    private UserArchivesPresenter userArchivesPresenter;
    private ImageView mBack;
    /**
     * 面神经炎
     */
    private TextView mZhengzhuang;
    /**
     * 面神经炎
     */
    private TextView mBingshi;
    /**
     * 面神经炎
     */
    private TextView mJbingshi;
    /**
     * 面神经炎
     */
    private TextView mYiyuan;
    /**
     * 面神经炎
     */
    private TextView mTime;
    /**
     * 面神经炎
     */
    private TextView mGuocheng;
    private NineGridTestLayout mLayoutNineGrid;
    /**
     * 添加
     */
    private TextView mAdd;
    private LinearLayout mNull;
    private LinearLayout file;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_file;
    }

    @Override
    protected void initView() {
        userArchivesPresenter = new UserArchivesPresenter(new UserArchives());

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
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

    public class UserArchives implements DataCall<Result<UserArchivesBean>> {
        @Override
        public void success(Result<UserArchivesBean> data, Object... args) {
            UserArchivesBean result = data.getResult();
            if (null != result) {
                file.setVisibility(View.VISIBLE);
                mNull.setVisibility(View.GONE);
                mZhengzhuang.setText(result.diseaseMain);
                mBingshi.setText(result.diseaseNow);
                mJbingshi.setText(result.diseaseBefore);
                mYiyuan.setText(result.treatmentHospitalRecent);
                long treatmentStartTime = result.treatmentStartTime;
                long treatmentEndTime = result.treatmentEndTime;
                String start = DateUtils.timeStamp2Date(treatmentStartTime, "yyyy.MM.dd");
                String end = DateUtils.timeStamp2Date(treatmentEndTime, "yyyy.MM.dd");
                String startSubstring = start.substring(4, start.length());
                String endSubstring = start.substring(4, start.length());
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
