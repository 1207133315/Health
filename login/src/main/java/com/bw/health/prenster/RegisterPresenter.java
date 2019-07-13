package com.bw.health.prenster;

import com.bw.health.Irequset;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

public class RegisterPresenter extends WDPresenter<Irequset> {
    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.register((String) args[0],(String) args[1],(String) args[2],(String) args[3],(String) args[4]);
    }
}
