package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/29 20:30
 */
public class UserArchivesPresenter extends WDPresenter<Minerequest> {
    public UserArchivesPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.userArchives((long)args[0],(String)args[1]);
    }
}
