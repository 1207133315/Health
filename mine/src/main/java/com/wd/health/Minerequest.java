package com.wd.health;

import com.bw.health.bean.MationBean;
import com.bw.health.bean.Result;
import com.wd.health.bean.CollectCircleBean;
import com.wd.health.bean.CollectVideoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Minerequest {
    // 查询用户当天是否签到
    @GET("user/verify/v1/whetherSignToday")
    Observable<Result> whetherSignToday(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId);

    //用户签到
    @POST("user/verify/v1/addSign")
    Observable<Result> addSign(@Header("userId") int userId,
                               @Header("sessionId") String sessionId);

    //上传头像
    @Multipart
    @POST("user/verify/v1/modifyHeadPic")
    Observable<Result> modifyHeadPic(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Part MultipartBody.Part MultipartFile);

    //查询用户关注医生列表
    @GET("user/verify/v1/findUserDoctorFollowList")
    Observable<Result>findUserDoctorFollowList(@Header("userId") int userId,
                                               @Header("sessionId") String sessionId,
                                               @Query("page")int page,
                                               @Query("count")int count);

    //查询用户收藏资讯列表

    @GET("user/verify/v1/findUserInfoCollectionList")
    Observable<Result<List<MationBean>>> findZiXun(@Header("userId") long userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("page") int page,
                                                   @Query("count")int count
                                                   );

    //查询用户收藏病友圈列表
    @GET("user/verify/v1/findUserSickCollectionList")
    Observable<Result<List<CollectCircleBean>>> findCircle(@Header("userId") long userId,
                                                           @Header("sessionId") String sessionId,
                                                           @Query("page") int page,
                                                       @Query("count")int count);
    //查询用户收藏健康讲堂列表
    @GET("user/verify/v1/findVideoCollectionList")
    Observable<Result<List<CollectVideoBean>>> findVideo(@Header("userId") long userId,
                                                         @Header("sessionId") String sessionId,
                                                         @Query("page") int page,
                                                         @Query("count")int count);
    //取消资讯收藏
    @HTTP(method = "DELETE", path = "user/verify/v1/cancelInfoCollection", hasBody = true)
    Observable<Result> unShouchang(@Header("userId") long userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("infoId") long infoId);


    //取消收藏视频
    @HTTP(method = "DELETE", path = " user/verify/v1/cancelVideoCollection", hasBody = true)
    Observable<Result> unVideo(@Header("userId") long userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("videoId") long videoId);
    //取消收藏病友圈
    @HTTP(method = "DELETE", path = " user/verify/v1/cancelSickCollection", hasBody = true)
    Observable<Result> unCircle(@Header("userId") long userId,
                               @Header("sessionId") String sessionId,
                               @Query("sickCircleId") long sickCircleId);
}
