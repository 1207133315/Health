package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/08/06 21:18
 */
public class BindCardPresenter extends WDPresenter<Minerequest> {
    public BindCardPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.bingCard((long)args[0],(String)args[1],(String)args[2],(String)args[3],(int)args[4]);
    }
}
