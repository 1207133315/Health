package com.wd.health.messagelist.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.messagelist.Messagerequest;

import io.reactivex.Observable;

public class FindSystemNoticeListPresenter extends WDPresenter<Messagerequest> {
    public FindSystemNoticeListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findSystemNoticeList((int)args[0],(String) args[1],(int)args[2],(int)args[3]);
    }
}
