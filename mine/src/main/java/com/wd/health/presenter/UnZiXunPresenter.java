package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/25 20:37
 */
public class UnZiXunPresenter extends WDPresenter<Minerequest> {
    public UnZiXunPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.unShouchang((long)args[0],(String)args[1],(long)args[2]);
    }
}
