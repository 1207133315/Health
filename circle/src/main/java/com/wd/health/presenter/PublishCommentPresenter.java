package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.model.CircleIRquest;

import io.reactivex.Observable;

public class PublishCommentPresenter extends WDPresenter<CircleIRquest> {

    public PublishCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.publishComment((String) args[0], (String) args[1], (String) args[2], (String) args[3]);
    }
}
