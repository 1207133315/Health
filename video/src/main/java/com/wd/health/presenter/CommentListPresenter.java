package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.VideoRequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/18 20:13
 */
public class CommentListPresenter extends WDPresenter<VideoRequest> {
    public CommentListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.findCommentList((long)args[0]);
    }
}
