package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.InterrogationRequest;
import com.wd.health.activity.IMRequest;

import io.reactivex.Observable;

/**
 * com.wd.health.activity.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/31 16:07
 */
public class ConsultDoctorPresenter extends WDPresenter<InterrogationRequest> {
    public ConsultDoctorPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.consultDoctor((long)args[0],(String)args[1],(int)args[2]);
    }
}
