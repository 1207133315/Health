package com.wd.health.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.UserHealthTestPresenter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;

@Route(path = "/CepingActivity/")
public class CepingActivity extends WDActivity {


    @BindView(R2.id.web)
    WebView web;
    private DoTaskPresenter presenter;
    private List<LoginBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ceping;
    }

    @Override
    protected void initView() {
        presenter = new DoTaskPresenter(new DoTask());
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        UserHealthTestPresenter userHealthTestPresenter = new UserHealthTestPresenter(new UserHealthTest());
        userHealthTestPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId());
    }

    @Override
    protected void destoryData() {

    }

    public class UserHealthTest implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            String url= (String) result.getResult();
            web.loadUrl(url);
            presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(),1005);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public class DoTask implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            Toast.makeText(CepingActivity.this,  result.getResult().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
