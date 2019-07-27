package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.model.CircleIRquest;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/26
 * @Description: 发布病友圈
 */
public class PublishSickCirclePresenter extends WDPresenter<CircleIRquest> {
    public PublishSickCirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.publishSickCircle(
                (String)args[0],(String)args[1],(RequestBody) args[2]);
    }
}
