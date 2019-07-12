package com.bw.health.presenter;

import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/11 15:10
 */
public class ShowBannerPresenter extends WDPresenter<IAppRequest> {
    public ShowBannerPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.showBanner();
    }


}
