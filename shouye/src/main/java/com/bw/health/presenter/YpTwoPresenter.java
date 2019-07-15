package com.bw.health.presenter;

import com.bw.health.ShouYeRequest;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;

import io.reactivex.Observable;

/**
 * com.bw.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/15 10:00
 */
public class YpTwoPresenter extends WDPresenter<ShouYeRequest> {
    public YpTwoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findDrugsKnowledgeList((long)args[0],(int)args[1],(int)args[2]);
    }
}
