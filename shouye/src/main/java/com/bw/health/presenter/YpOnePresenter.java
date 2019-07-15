package com.bw.health.presenter;

import com.bw.health.ShouYeRequest;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/15 10:00
 */
public class YpOnePresenter extends WDPresenter<ShouYeRequest> {
    public YpOnePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDrugsCategoryList();
    }
}
