package com.bw.health.presenter;

import com.bw.health.ShouYeRequest;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/15 14:44
 */
public class WatchInfoRewardsPresenter extends WDPresenter<ShouYeRequest> {
    public WatchInfoRewardsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.watchInfoRewards((long)args[0],(String)args[1],(long)args[2]);
    }
}
