package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class ModifyNickNamePresenter extends WDPresenter<Minerequest> {
    public ModifyNickNamePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.modifyNickName((int)args[0],(String)args[1],(String)args[2]);
    }
}
