package com.bw.health.http;

import com.bw.health.bean.BannerBean;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.PlateBean;

import com.bw.health.bean.Result;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IAppRequest {
    /**-------------首页--------------*/
    @GET("share/v1/bannersShow")
    Observable<Result<List<BannerBean>>> showBanner();
    @GET("share/information/v1/findInformationPlateList")
    Observable<Result<List<PlateBean>>> plateList();

    @GET("share/information/v1/findInformationList")
    Observable<Result<List<MationBean>>> mationList(@Query("plateId")long plateId,
                                                    @Query("page") int page,
                                                    @Query("count")int count);
    /**-------------首页--------------*/


}
