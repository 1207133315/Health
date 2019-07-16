package com.wd.health;

import com.bw.health.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Irequest  {
    // 查询用户当天是否签到
    @GET("user/verify/v1/whetherSignToday")
    Observable<Result> whetherSignToday(@Header("userId")int userId,
                                     @Header("sessionId")String sessionId);
    //用户签到
    @POST("user/verify/v1/addSign")
    Observable<Result> addSign(@Header("userId")int userId,
                                        @Header("sessionId")String sessionId);
}
