package com.wd.health.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
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
import com.bw.health.util.BitmapUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.presenter.AddSignPresenter;
import com.wd.health.presenter.DoTaskPresenter;
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
    private DoTaskPresenter doTaskPresenter;
    private Intent intent;
    private Bitmap bitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    protected void initView() {
        whetherSignTodayPresenter = new WhetherSignTodayPresenter(new WhetherSignToday());
        addSignPresenter = new AddSignPresenter(new AddSign());
        doTaskPresenter = new DoTaskPresenter(new Dotask());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        list = loginBeanDao.queryBuilder().list();
        if (list != null&&list.size()>0) {
            loginBean = list.get(0);
            String headPic = loginBean.getHeadPic();
            head.setImageURI(headPic);
            name.setText(loginBean.getNickName());
            whetherSignTodayPresenter.reqeust(loginBean.getId().intValue(), loginBean.getSessionId());
        }else {
            bitmap = BitmapUtils.readBitMap(WDApplication.getContext(), R.mipmap.common_icon_boy_n);
            head.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back, R2.id.lingdang, R2.id.head, R2.id.guanzhu, R2.id.renwu, R2.id.shezhiguanli, R2.id.jianyi, R2.id.shipin, R2.id.bingyouquan, R2.id.second, R2.id.dangan, R2.id.qianbao, R2.id.shoucang, R2.id.dangqian, R2.id.lishi, R2.id.qd})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            intentByRouter("/HomeActivity/");
            finish();
        } else if (i == R.id.lingdang) {

        } else if (i == R.id.head) {

        } else if (i == R.id.guanzhu) {
            intentByRouter("/GuanzhuActivity/");

        } else if (i == R.id.renwu) {
            intent = new Intent(MineActivity.this,TaskActivity.class);
            startActivity(intent);
        } else if (i == R.id.shezhiguanli) {
             intent=new Intent(MineActivity.this,SetupActivity.class);
            startActivity(intent);
        } else if (i == R.id.jianyi) {
            //查询我的被采纳的建议
            intentByRouter("/BeAdoptedActivity/");

        } else if (i == R.id.shipin) {
            intent=new Intent(MineActivity.this,MyVideoActivity.class);
            startActivity(intent);
        } else if (i == R.id.bingyouquan) {
            //跳转我的病友圈
            startActivity(new Intent(MineActivity.this,PatientsCircleActivity.class));

        } else if (i == R.id.second) {
        } else if (i == R.id.dangan) {
            intent=new Intent(MineActivity.this,MyFileActivity.class);
            startActivity(intent);
        } else if (i == R.id.qianbao) {
            startActivity(new Intent(MineActivity.this,MyWallteActivity.class));
        } else if (i == R.id.shoucang) {
             intent=new Intent(MineActivity.this,MyCollectActivity.class);
            startActivity(intent);
        } else if (i == R.id.dangqian) {
        } else if (i == R.id.lishi) {

        } else if (i == R.id.qd) {
            if (qd.getText().equals("已签到")){

                Toast.makeText(this, "您今日已签到，不能重复签到", Toast.LENGTH_SHORT).show();
            }else {
                if (list!=null&&list.size()>0){
                    addSignPresenter.reqeust(loginBean.getId().intValue(),loginBean.getSessionId());
                }else {
                    intentByRouter("/LoginActivity/");
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
            Toast.makeText(MineActivity.this,result.getMessage(), Toast.LENGTH_SHORT).show();
            qd.setText("已签到");
            doTaskPresenter.reqeust(list.get(0).getId().intValue(),list.get(0).getSessionId(),1001);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(MineActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("请先登陆")){
                intentByRouter("/LoginActivity/");
            }
        }

    }
    //做任务
    public class Dotask implements DataCall{

        @Override
        public void success(Object data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        whetherSignTodayPresenter.dataCall=null;
        whetherSignTodayPresenter=null;
       addSignPresenter.dataCall=null;
       addSignPresenter=null;
        list=null;
         loginBean=null;
         doTaskPresenter.dataCall=null;
         doTaskPresenter=null;
        intent=null;
        bitmap=null;
    }
    //查询用户未读消息数
    public class FindUserNoticeReadNum implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            boolean t=false;
            Result<List<MessageBean>> result= (Result<List<MessageBean>>) data;
            List<MessageBean> list = result.getResult();
            for (MessageBean messageBean : list) {
                if (messageBean.getNotReadNum()>0){
                    t=true;
                    break;
                }
            }
            if (t){
                lingdang.setImageResource(R.mipmap.common_nav_message_white_s);
            }else {
                lingdang.setImageResource(R.mipmap.common_nav_message_white_n);
            }

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
