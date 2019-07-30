package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class DeleteVideoBuyPresenter extends WDPresenter<Minerequest> {

    public DeleteVideoBuyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.deleteVideoBuy((long)args[0],(String)args[1],(long)args[2]);
    }
}
