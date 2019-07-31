package com.bw.health.util;

import android.widget.Toast;

import com.bw.health.UpTokenPresenter;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDApplication;
import com.bw.health.exception.ApiException;

import java.util.List;

/**
 * com.bw.health.util
 *
 * @author 李宁康
 * @date 2019 2019/07/31 19:27
 */
public class UpToken {
    private static UpToken upToken=new UpToken();
    private final UpTokenPresenter upTokenPresenter;
    private  LoginBean loginBean;

    public static UpToken getUpToken() {
        return upToken;
    }
    private UpToken(){
        upTokenPresenter = new UpTokenPresenter(new UpToken1());

    }
    public void addToken(String token){
         List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()>0){
            loginBean = loginBeans.get(0);
        }
        if (loginBean!=null){
            upTokenPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),token);
        }
    }

    public class UpToken1 implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }


}
