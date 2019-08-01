package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;
/**
 * @Auther: 郭亚杰
 * @Date:2019/8/1
 * @Description: 查询我的被采纳的建议
 */
public class BeAdoptedPresenter extends WDPresenter<Minerequest> {


    public BeAdoptedPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findMyAdoptedCommentList((String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}
