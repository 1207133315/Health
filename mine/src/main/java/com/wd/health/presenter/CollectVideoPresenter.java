package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/24 20:59
 */
public class CollectVideoPresenter extends WDPresenter<Minerequest> {
    public CollectVideoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findVideo((long)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}
