package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

public class UpdateUserSexPresenter extends WDPresenter<Minerequest> {
    public UpdateUserSexPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.updateUserSex((int)args[0],(String)args[1],(int)args[2]);
    }
}
