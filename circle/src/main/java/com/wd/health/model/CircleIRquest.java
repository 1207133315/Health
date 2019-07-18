package com.wd.health.model;

import com.bw.health.bean.Result;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.CircleInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CircleIRquest {

    //-------------病友圈接口---请勿修改-----------
    //查询病友圈详情
    @GET("user/sickCircle/v1/findSickCircleInfo")
    Observable<Result<CircleInfoBean>> findSickCircleInfo(@Query("sickCircleId") String sickCircleId);

    // 查询病友圈评论列表
    @GET("user/sickCircle/v1/findSickCircleCommentList?page=1&count=10")
    Observable<Result<List<CircleCommentListBean>>> findSickCircleCommentList(
            @Query("sickCircleId") String sickCircleId);

    //病友圈发表评论
    @POST("user/sickCircle/verify/v1/publishComment")
    Observable<Result> publishComment(@Header("userId") String userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("sickCircleId") String sickCircleId,
                                      @Query("content") String content);

    //-------------病友圈接口---请勿修改-----------
}
