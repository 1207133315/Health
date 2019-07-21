package com.wd.doctor.interrogation.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.doctor.interrogation.IRequest;

import io.reactivex.Observable;

public class FindDoctorListPresenter extends WDPresenter<IRequest> {
    public FindDoctorListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDoctorList((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4],(int)args[5],(int)args[6]);
    }
}
