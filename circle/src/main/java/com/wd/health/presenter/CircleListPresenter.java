package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

public class CircleListPresenter extends WDPresenter<IAppRequest> {
    public CircleListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findSickCircleList((String) args[0]);

    }
}
