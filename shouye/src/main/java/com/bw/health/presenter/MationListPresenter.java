package com.bw.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/12 10:49
 */
public class MationListPresenter extends WDPresenter<IAppRequest> {
    public MationListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.mationList((long)args[0],(int)args[1],(int)args[2]);
    }
}
