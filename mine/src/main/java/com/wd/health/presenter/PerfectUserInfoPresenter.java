package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class PerfectUserInfoPresenter extends WDPresenter<Minerequest> {
    public PerfectUserInfoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.perfectUserInfo((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
