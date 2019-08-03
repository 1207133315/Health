package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;
/**
 * @Auther: 郭亚杰
 * @Date:2019/7/30
 * @Description:查看我的病友圈发帖列表
 */

//查看我的病友圈发帖列表
public class PatientsCirclePresenter extends WDPresenter<Minerequest> {
    public PatientsCirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findMySickCircleList((String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}
