package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.bw.health.http.IAppRequest;

import io.reactivex.Observable;
/**
 * @Auther: 郭亚杰
 * @Date:2019/7/23
 * @Description: 病友圈列表展示
 */
public class CircleListPresenter extends WDPresenter<IAppRequest> {
    public CircleListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findSickCircleList((String) args[0],(String) args[1],(String) args[2]);

    }
}
