package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.VideoRequest;

import io.reactivex.Observable;

/**
 * com.wd.health.presenter
 *
 * @author 李宁康
 * @date 2019 2019/07/16 17:27
 */
public class VideoListPresenter extends WDPresenter<VideoRequest> {
    public VideoListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.videoList((long)args[0],(String)args[1],(long)args[2],(int)args[3],(int)args[4]);
    }
}
