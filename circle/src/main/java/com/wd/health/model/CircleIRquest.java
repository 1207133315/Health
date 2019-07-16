package com.wd.health.model;

import com.bw.health.bean.Result;
import com.wd.health.bean.CircleInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CircleIRquest {

    //-------------病友圈接口---请勿修改-----------
    //查询病友圈详情
    @GET("user/sickCircle/v1/findSickCircleInfo")
    Observable<Result<CircleInfoBean>> findSickCircleInfo(@Query("sickCircleId") String sickCircleId);


    //-------------病友圈接口---请勿修改-----------
}
