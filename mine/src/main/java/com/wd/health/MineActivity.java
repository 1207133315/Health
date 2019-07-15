package com.wd.health;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/MineActivity/")
public class MineActivity extends WDActivity {
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.lingdang)
    ImageView lingdang;
    @BindView(R2.id.head)
    SimpleDraweeView head;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.bw)
    TextView bw;
    @BindView(R2.id.guanzhu)
    ImageView guanzhu;
    @BindView(R2.id.renwu)
    ImageView renwu;
    @BindView(R2.id.shezhiguanli)
    ImageView shezhiguanli;
    @BindView(R2.id.third)
    LinearLayout third;
    @BindView(R2.id.jianyi)
    ImageView jianyi;
    @BindView(R2.id.shipin)
    ImageView shipin;
    @BindView(R2.id.bingyouquan)
    ImageView bingyouquan;
    @BindView(R2.id.second)
    LinearLayout second;
    @BindView(R2.id.dangan)
    ImageView dangan;
    @BindView(R2.id.qianbao)
    ImageView qianbao;
    @BindView(R2.id.shoucang)
    ImageView shoucang;
    @BindView(R2.id.first)
    LinearLayout first;
    @BindView(R2.id.dangqian)
    RelativeLayout dangqian;
    @BindView(R2.id.lishi)
    RelativeLayout lishi;

    @Override
    protected int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    protected void initView() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "login");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        LoginBeanDao loginBeanDao = daoSession.getLoginBeanDao();
        List<LoginBean> list = loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).list();
        if (list != null) {
            LoginBean loginBean = list.get(0);
            String headPic = loginBean.getHeadPic();
            head.setImageURI(headPic);
            name.setText(loginBean.getNickName());
        }

    }

    @Override
    protected void destoryData() {

    }



    @OnClick({R2.id.back, R2.id.lingdang, R2.id.head, R2.id.guanzhu, R2.id.renwu, R2.id.shezhiguanli, R2.id.jianyi, R2.id.shipin, R2.id.bingyouquan, R2.id.second, R2.id.dangan, R2.id.qianbao, R2.id.shoucang, R2.id.dangqian, R2.id.lishi})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
        } else if (i == R.id.lingdang) {
        } else if (i == R.id.head) {
        } else if (i == R.id.guanzhu) {
        } else if (i == R.id.renwu) {
        } else if (i == R.id.shezhiguanli) {
        } else if (i == R.id.jianyi) {
        } else if (i == R.id.shipin) {
        } else if (i == R.id.bingyouquan) {
        } else if (i == R.id.second) {
        } else if (i == R.id.dangan) {
        } else if (i == R.id.qianbao) {
        } else if (i == R.id.shoucang) {
        } else if (i == R.id.dangqian) {
        } else if (i == R.id.lishi) {
        }
    }
}
