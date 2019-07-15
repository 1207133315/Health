package com.bw.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/12 09:08
 */
public class PlateListPresenter extends WDPresenter<IAppRequest> {
    public PlateListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.plateList();
    }
}
