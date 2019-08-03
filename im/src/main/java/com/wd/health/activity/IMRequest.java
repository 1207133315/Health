package com.wd.health.activity;

import com.bw.health.bean.Result;
import com.wd.health.activity.bean.MessageBean;
import com.wd.health.activity.bean.UserRecordBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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
    @POST("user/inquiry/verify/v1/pushMessage")
    @FormUrlEncoded
    Observable<Result> pushText(@Header("userId") long userId,
                                @Header("sessionId") String sessionId,
                                @Field("inquiryId") int inquiryId,
                                @Field("msgContent") String msgContent,
                                @Field("type") int type,
                                @Field("doctorId") int doctorId);

    //用户查看当前问诊
    @GET("user/inquiry/verify/v1/findCurrentInquiryRecord")
    Observable<Result<UserRecordBean>> findNowWZ(@Header("userId") long userId,
                                                 @Header("sessionId") String sessionId);

    //结束问诊
    @PUT("user/inquiry/verify/v1/endInquiry")
    Observable<Result> endInQuiry(@Header("userId") long userId,
                                  @Header("sessionId") String sessionId,
                                  @Query("recordId") int recordId);
    //查询历史聊天记录
    @GET("user/inquiry/verify/v1/findInquiryRecordList")
    Observable<Result<List<MessageBean>>> findMessage(@Header("userId") long userId,
                                                      @Header("sessionId") String sessionId,
                                                      @Query("inquiryId") int inquiryId,
                                                      @Query("page") int page,
                                                      @Query("count")int count);
}
