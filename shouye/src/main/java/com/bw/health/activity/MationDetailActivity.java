package com.bw.health.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.MationDetail;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.AddInfoPresenter;
import com.bw.health.presenter.MationDetailPresenter;
import com.bw.health.presenter.WatchInfoRewardsPresenter;
import com.bw.health.util.GetDaoUtil;


import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MationDetailActivity extends WDActivity {


    @BindView(R2.id.title)
    TextView title;
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.head)
    ImageView head;
    @BindView(R2.id.webView)
    WebView webView;
    @BindView(R2.id.yes)
    ImageView yes;
    @BindView(R2.id.close)
    ImageView close;
    @BindView(R2.id.title2)
    TextView title2;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.time)
    TextView time;
    @BindView(R2.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R2.id.fenxaing)
    ImageView fenxaing;
    @BindView(R2.id.shoucang)
    RadioButton shoucang;
    private LoginBean user;
    private MationDetailPresenter mationDetailPresenter;
    private long id;
    private String js;
    private AddInfoPresenter addInfoPresenter;
    @BindView(R2.id.layout_main)
    RelativeLayout layout_main;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                watchInfoRewardsPresenter.reqeust(user.getId(),user.getSessionId(),id);
            }
        }
    };
    private WatchInfoRewardsPresenter watchInfoRewardsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mation_detail;
    }

    @Override
    protected void initView() {
        id = getIntent().getLongExtra("id", 1);
        LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
        user = userDao.queryBuilder().where(LoginBeanDao.Properties.Islogin.eq(true)).unique();

        watchInfoRewardsPresenter = new WatchInfoRewardsPresenter(new Reward());

        setWeb();
        if (user != null) {
            Glide.with(MationDetailActivity.this).load(user.getHeadPic()).into(head);
            mationDetailPresenter = new MationDetailPresenter(new MationDetail());
            mationDetailPresenter.reqeust(user.getId(), user.getSessionId(), id);
        }
        setScrollview();

        //收藏实例初始化
        addInfoPresenter = new AddInfoPresenter(new AddInfo());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
            }
        });
    }

    public class Reward implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(MationDetailActivity.this, ""+data.getResult(), Toast.LENGTH_SHORT).show();
            yes.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
        }

        @Override
        public void fail(ApiException data, Object... args) {
           // Toast.makeText(MationDetailActivity.this, ""+data.getMessage()+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
            if (data.getDisplayMessage().equals("获得奖励失败")){
                yes.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
            }

        }
    }

    private void setScrollview() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 100) {
                    title.setVisibility(View.GONE);
                    back.setVisibility(View.GONE);
                    head.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    head.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setWeb() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        // 找到img标签
// 逐个改变
// 宽度改为100%
        js = "<script type='text/javascript'> window.onload = function() {var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";

    }


    @Override
    protected void destoryData() {

    }



    public class MationDetail implements DataCall<Result<com.bw.health.bean.MationDetail>> {
        @Override
        public void success(Result<com.bw.health.bean.MationDetail> data, Object... args) {
            title2.setText(data.getResult().title);
            String content = data.getResult().content;
            content = content.replaceAll("width=\"\\d+\"", "width=\"100%\"").replaceAll("height=\"\\d+\"", "height=\"auto\"");
            webView.loadDataWithBaseURL(null, js+content , "text/html", "utf-8", null);
            name.setText(data.getResult().source);
            long releaseTime = data.getResult().releaseTime;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = sdf.format(new Date(releaseTime));
            time.setText(format);
             int whetherCollection = data.getResult().whetherCollection;
             if (whetherCollection==1){
                 shoucang.setChecked(true);
             }else {
                 shoucang.setChecked(false);
             }
             handler.sendEmptyMessageDelayed(1,10000);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


    @OnClick({R2.id.back, R2.id.head, R2.id.fenxaing, R2.id.shoucang})
    public void onClick(View v) {
        int i = v.getId();

        int i1 = v.getId();
        if (i1 == R.id.back) {
            finish();
        } else if (i1 == R.id.head) {

        } else if (i1 == R.id.fenxaing) {

        } else if (i1 == R.id.shoucang) {
            setShoucang();
        } else {
        }
    }

    private void setShoucang() {
        if (shoucang.isChecked()){
            addInfoPresenter.reqeust(user.getId(),user.getSessionId(),id);
        }else {

        }

    }

    public class AddInfo implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(MationDetailActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
