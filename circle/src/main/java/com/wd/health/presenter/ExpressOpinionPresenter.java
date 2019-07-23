package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.model.CircleIRquest;

import io.reactivex.Observable;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/22
 * @Description: 发表支持的观点
 */
public class ExpressOpinionPresenter extends WDPresenter<CircleIRquest> {
    public ExpressOpinionPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.expressOpinion(
                (String)args[0],(String)args[1],(String)args[2],(String)args[3]);

    }
}
