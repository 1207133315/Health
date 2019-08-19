package com.wd.health.messagelist.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.messagelist.Messagerequest;

import io.reactivex.Observable;

public class ModifyAllStatusPresenter extends WDPresenter <Messagerequest>{
    public ModifyAllStatusPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.modifyAllStatus((int)args[0],(String)args[1]);
    }
}
