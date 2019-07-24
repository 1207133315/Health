package com.wd.health.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDApplication;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.utils.CacheUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupActivity extends WDActivity {


    @BindView(R2.id.head)
    SimpleDraweeView head;
    @BindView(R2.id.grxx)
    RelativeLayout grxx;
    @BindView(R2.id.xgmm)
    RelativeLayout xgmm;
    @BindView(R2.id.qchc)
    RelativeLayout qchc;
    @BindView(R2.id.pmld)
    RelativeLayout pmld;
    @BindView(R2.id.bbjc)
    RelativeLayout bbjc;
    @BindView(R2.id.bzzx)
    RelativeLayout bzzx;
    @BindView(R2.id.gywm)
    RelativeLayout gywm;
    @BindView(R2.id.yqhy)
    RelativeLayout yqhy;
    @BindView(R2.id.tcdl)
    RelativeLayout tcdl;
    @BindView(R2.id.daxiao)
    TextView daxiao;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.back)
    ImageView back;
    private List<LoginBean> list;
    private LoginBean loginBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setup;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        list = loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).list();
        if (list != null && list.size() > 0) {
            loginBean = list.get(0);
            String headPic = loginBean.getHeadPic();
            head.setImageURI(headPic);
            name.setText(loginBean.getNickName());
        } else {
            head.setImageResource(R.mipmap.common_icon_boy_n);
        }

        /**
         * 获取缓存大小
         */
        String totalCacheSize = null;
        try {
            totalCacheSize = CacheUtil.getTotalCacheSize(SetupActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        daxiao.setText(totalCacheSize);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R2.id.grxx, R2.id.xgmm, R2.id.qchc, R2.id.pmld, R2.id.bbjc, R2.id.bzzx, R2.id.gywm, R2.id.yqhy, R2.id.tcdl,R2.id.back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.grxx) {
            Intent intent = new Intent(this, MineMessageActivity.class);
            startActivity(intent);
        } else if (i == R.id.xgmm) {
        } else if (i == R.id.qchc) {
            new AlertDialog.Builder(SetupActivity.this)
                    .setTitle("清除缓存")
                    .setMessage("是否确认清除缓存")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //清除缓存的方法
                            CacheUtil.clearAllCache(SetupActivity.this);
                            daxiao.setText("0M");
                            Toast.makeText(SetupActivity.this, "清除成功", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else if (i == R.id.pmld) {
            Intent intent = new Intent(SetupActivity.this, PingmuActivity.class);
            startActivity(intent);
        } else if (i == R.id.bbjc) {
        } else if (i == R.id.bzzx) {
        } else if (i == R.id.gywm) {
        } else if (i == R.id.yqhy) {
        } else if (i == R.id.tcdl) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SetupActivity.this);
            builder.setTitle("退出登录")
                    .setMessage("确定退出登录吗?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
                            loginBeanDao.deleteAll();
                            Intent intent = new Intent(SetupActivity.this, SetupActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        }else if (i==R.id.back){
            finish();
        }
    }


}
