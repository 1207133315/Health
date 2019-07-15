package com.bw.health.prenster;

import com.bw.health.Irequset;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

public class LoginPresenter extends WDPresenter<Irequset> {

    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.login((String) args[0],(String)args[1]);
    }
}
