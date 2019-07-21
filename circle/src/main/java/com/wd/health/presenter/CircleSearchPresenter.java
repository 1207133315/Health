package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.model.CircleIRquest;

import io.reactivex.Observable;

public class CircleSearchPresenter extends WDPresenter<CircleIRquest> {
    public CircleSearchPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.searchSickCircle((String) args[0]);
    }
}
