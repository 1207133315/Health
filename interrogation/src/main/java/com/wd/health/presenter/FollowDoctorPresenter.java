package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.InterrogationRequest;

import io.reactivex.Observable;

public class FollowDoctorPresenter extends WDPresenter<InterrogationRequest> {
    public FollowDoctorPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.followDoctor((int)args[0],(String)args[1],(int)args[2]);
    }
}
