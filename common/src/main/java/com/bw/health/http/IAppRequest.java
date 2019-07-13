package com.bw.health.http;

import com.bw.health.bean.BannerBean;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IAppRequest {

    @GET("share/v1/bannersShow")
    Observable<Result<List<BannerBean>>> showBanner();

}
