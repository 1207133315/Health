package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.messagelist.Messagerequest;

import io.reactivex.Observable;

public class FindUserNoticeReadNumPresenter extends WDPresenter<Messagerequest> {
    public FindUserNoticeReadNumPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findUserNoticeReadNum((int)args[0],(String)args[1]);
    }
}
