package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/30 20:37
 */
public class UpArchivesPresenter extends WDPresenter<Minerequest> {
    public UpArchivesPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.upArchives((long)args[0],(String)args[1],(RequestBody)args[2]);
    }
}
