package com.wd.health.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDApplication;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.presenter.AddSignPresenter;
import com.wd.health.presenter.WhetherSignTodayPresenter;

import java.util.List;

import butterknife.BindView;
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
    @BindView(R2.id.qd)
    Button qd;
    private WhetherSignTodayPresenter whetherSignTodayPresenter;
    private AddSignPresenter addSignPresenter;
    private List<LoginBean> list;
    private LoginBean loginBean;

    @Override
    protected int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    protected void initView() {
        whetherSignTodayPresenter = new WhetherSignTodayPresenter(new WhetherSignToday());
        addSignPresenter = new AddSignPresenter(new AddSign());


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        list = loginBeanDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).list();
        if (list != null&&list.size()>0) {
            loginBean = list.get(0);
            String headPic = loginBean.getHeadPic();
            head.setImageURI(headPic);
            name.setText(loginBean.getNickName());
            whetherSignTodayPresenter.reqeust(loginBean.getId().intValue(), loginBean.getSessionId());
        }else {
            intentByRouter("/LoginActivity/");
            finish();
        }
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back, R2.id.lingdang, R2.id.head, R2.id.guanzhu, R2.id.renwu, R2.id.shezhiguanli, R2.id.jianyi, R2.id.shipin, R2.id.bingyouquan, R2.id.second, R2.id.dangan, R2.id.qianbao, R2.id.shoucang, R2.id.dangqian, R2.id.lishi, R2.id.qd})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.lingdang) {
        } else if (i == R.id.head) {

        } else if (i == R.id.guanzhu) {
        } else if (i == R.id.renwu) {
        } else if (i == R.id.shezhiguanli) {
            Intent intent=new Intent(MineActivity.this,SetupActivity.class);
            startActivity(intent);
        } else if (i == R.id.jianyi) {
        } else if (i == R.id.shipin) {
        } else if (i == R.id.bingyouquan) {
        } else if (i == R.id.second) {
        } else if (i == R.id.dangan) {
        } else if (i == R.id.qianbao) {
        } else if (i == R.id.shoucang) {
        } else if (i == R.id.dangqian) {
        } else if (i == R.id.lishi) {

        } else if (i == R.id.qd) {
            if (qd.getText().equals("已签到")){
                Toast.makeText(this, "您今日已签到，不能重复签到", Toast.LENGTH_SHORT).show();
            }else {
                if (list!=null&&list.size()>0){
                    addSignPresenter.reqeust(loginBean.getId().intValue(),loginBean.getSessionId());
                }
            }
        }
    }

    //查询用户当天是否签到
    public class WhetherSignToday implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result result = (Result) data;
            if ((Double)result.getResult()==1.0){
                qd.setText("已签到");
            }else {

            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }

    }
    //用户签到
    public class AddSign implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result result = (Result) data;
            Toast.makeText(MineActivity.this, "result.getResult():" + result.getResult(), Toast.LENGTH_SHORT).show();
            qd.setText("已签到");
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(MineActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }
        }

    }
}
