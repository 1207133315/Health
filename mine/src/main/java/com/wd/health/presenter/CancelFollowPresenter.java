package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;


import io.reactivex.Observable;

public class CancelFollowPresenter extends WDPresenter<Minerequest> {
    public CancelFollowPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.cancelFollow((int)args[0],(String)args[1],(int)args[2]);
    }
}
