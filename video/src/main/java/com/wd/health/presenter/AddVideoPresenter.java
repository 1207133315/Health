package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.VideoRequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/17 10:50
 */
public class AddVideoPresenter extends WDPresenter<VideoRequest> {
    public AddVideoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.addVideoCollection((long)args[0],(String)args[1],(long)args[2]);
    }
}
