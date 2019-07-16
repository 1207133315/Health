package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Irequest;

import io.reactivex.Observable;

public class AddSignPresenter extends WDPresenter<Irequest> {

    public AddSignPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.addSign((int)args[0],(String)args[1]);
    }
}
