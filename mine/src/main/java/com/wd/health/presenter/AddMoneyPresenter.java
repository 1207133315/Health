package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class AddMoneyPresenter extends WDPresenter<Minerequest> {

    public AddMoneyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.add((long)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}
