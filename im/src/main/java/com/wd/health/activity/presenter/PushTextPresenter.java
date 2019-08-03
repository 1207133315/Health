package com.wd.health.activity.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.activity.IMRequest;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * com.wd.health.activity.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/31 20:57
 */
public class PushTextPresenter extends WDPresenter<IMRequest> {
    public PushTextPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {

        return iRequest.pushText((long)args[0],(String)args[1],(int)args[2],(String)args[3],(int)args[4],(int)args[5]);
    }
}
