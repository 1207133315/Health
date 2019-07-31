package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class CircleCommentPresenter extends WDPresenter<Minerequest> {
    public CircleCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.circlecomment((String)args[0],(String)args[1],(String)args[2]);
    }
}
