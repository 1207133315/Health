package com.wd.health.activity;

import com.bw.health.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * PACKAGE_NAME
 *
 * @author 李宁康
 * @date 2019 2019/07/31 16:03
 */
public interface IMRequest {

    //推送文本
    @POST("user/inquiry/verify/v1/pushTextMsg")
    @FormUrlEncoded
    Observable<Result> pushText(@Header("userId")long userId,
                                @Header("sessionId")String sessionId,
                                @Field("inquiryId") int inquiryId,
                                @Field("content")String content,
                                @Field("type")int type,
                                @Field("doctorId") int doctorId);

    //用户查看当前问诊
   // @GET("user/inquiry/verify/v1/findCurrentInquiryRecord")


}
