package com.wd.health.messagelist;


import com.bw.health.bean.Result;
import com.wd.health.messagelist.bean.MessageBean;
import com.wd.health.messagelist.bean.MessageDetailsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Messagerequest {
    //查询用户身份证信息
    @GET("user/verify/v1/findUserNoticeReadNum")
    Observable<Result<List<MessageBean>>> findUserNoticeReadNum(@Header("userId") int userId,
                                                                @Header("sessionId") String sessionId);

    //查询用户系统通知列表
    @GET("user/verify/v1/findSystemNoticeList")
    Observable<Result<List<MessageDetailsBean>>> findSystemNoticeList(@Header("userId") int userId,
                                                                      @Header("sessionId") String sessionId,
                                                                      @Query("page") int page,
                                                                      @Query("count") int count);
    //查询用户问诊通知列表
    @GET("user/verify/v1/findInquiryNoticeList")
    Observable<Result<List<MessageDetailsBean>>> findInquiryNoticeList(@Header("userId") int userId,
                                                                      @Header("sessionId") String sessionId,
                                                                      @Query("page") int page,
                                                                      @Query("count") int count);
    //查询用户H币通知列表
    @GET("user/verify/v1/findHealthyCurrencyNoticeList")
    Observable<Result<List<MessageDetailsBean>>> findHealthyCurrencyNoticeList(@Header("userId") int userId,
                                                                       @Header("sessionId") String sessionId,
                                                                       @Query("page") int page,
                                                                       @Query("count") int count);
    //修改消息状态为全部已读
    @PUT("user/verify/v1/modifyAllStatus")
    Observable<Result> modifyAllStatus(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId);
}
