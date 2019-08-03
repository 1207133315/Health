package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/31
 * @Description: 查询我的病友圈帖子的评论列表
 */
public class MySickCircleCommentListPresenter extends WDPresenter<Minerequest> {
    public MySickCircleCommentListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findMySickCircleCommentList((String)args[0],(String)args[1],
                (String)args[2],(String)args[3],(String)args[4]);
    }
}
