package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class UserHealthTestPresenter extends WDPresenter<Minerequest> {
    public UserHealthTestPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.userHealthTest((int)args[0],(String)args[1]);
    }
}
