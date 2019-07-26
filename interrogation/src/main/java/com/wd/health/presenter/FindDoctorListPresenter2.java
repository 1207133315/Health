package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.InterrogationRequest;

import io.reactivex.Observable;

public class FindDoctorListPresenter2 extends WDPresenter<InterrogationRequest> {
    public FindDoctorListPresenter2(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDoctorList2((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4],(int)args[5]);
    }
}
