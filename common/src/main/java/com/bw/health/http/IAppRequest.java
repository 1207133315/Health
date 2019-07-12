package com.bw.health.http;

import com.bw.health.bean.BannerBean;
import com.bw.health.bean.Result;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IAppRequest {

    @GET("share/v1/bannersShow")
    Observable<Result<List<BannerBean>>> showBanner();
}
