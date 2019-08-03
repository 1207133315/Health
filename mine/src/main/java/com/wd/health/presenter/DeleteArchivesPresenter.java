package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/30 21:14
 */
public class DeleteArchivesPresenter extends WDPresenter<Minerequest> {
    public DeleteArchivesPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.deleteArchives((long)args[0],(String)args[1],(long)args[2]);
    }
}
