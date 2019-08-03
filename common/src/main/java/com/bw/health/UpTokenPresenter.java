package com.bw.health;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;

/**
 * com.bw.health
 *
 * @author 李宁康
 * @date 2019 2019/07/31 19:30
 */
public class UpTokenPresenter extends WDPresenter<IAppRequest> {
    public UpTokenPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.upToken((long)args[0],(String)args[1],(String)args[2]);
    }
}
