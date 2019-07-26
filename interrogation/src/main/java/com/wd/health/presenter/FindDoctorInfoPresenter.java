package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.InterrogationRequest;

import io.reactivex.Observable;

public class FindDoctorInfoPresenter extends WDPresenter<InterrogationRequest> {
    public FindDoctorInfoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDoctorInfo((int)args[0],(String)args[1],(int)args[2]);
    }
}
