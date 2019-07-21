package com.wd.doctor.interrogation.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.doctor.interrogation.IRequest;

import io.reactivex.Observable;

public class FindDepartmentPresenter extends WDPresenter<IRequest> {
    public FindDepartmentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDepartment();
    }
}
