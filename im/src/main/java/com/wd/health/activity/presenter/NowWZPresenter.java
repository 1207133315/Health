package com.wd.health.activity.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.activity.IMRequest;

import io.reactivex.Observable;

/**
 * com.wd.health.activity.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/31 20:57
 */
public class NowWZPresenter extends WDPresenter<IMRequest> {
    public NowWZPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findNowWZ((long)args[0],(String)args[1]);
    }
}
