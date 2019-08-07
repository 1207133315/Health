package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class FindUserIdCardPresenter extends WDPresenter<Minerequest> {
    public FindUserIdCardPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findUserIdCard((int)args[0],(String)args[1]);
    }
}
