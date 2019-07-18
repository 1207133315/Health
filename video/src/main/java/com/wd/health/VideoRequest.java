package com.wd.health;

import com.bw.health.bean.Result;
import com.wd.health.bean.CommentBean;
import com.wd.health.bean.LeimuBean;
import com.wd.health.bean.VideoBean;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * com.wd.health
 *
 * @author 李宁康
 * @date 2019 2019/07/16 17:15
 */
public interface VideoRequest {
    //查询视频试看列表
    @GET("user/video/v1/findVideoVoList")
    Observable<Result<List<VideoBean>>> videoList(@Header("userId")long userId,@Header("sessionId")String sessionId,
                                                  @Query("categoryId")long categoryId,
                                                  @Query("page")int page,
                                                  @Query("count")int count
                                                  );
    //传讯健康讲堂类目
    @GET("user/video/v1/findVideoCategoryList")
    Observable<Result<List<LeimuBean>>> categoryList();

    //收藏视频
    @POST("user/video/verify/v1/addUserVideoCollection")
    @FormUrlEncoded
    Observable<Result> addVideoCollection(@Header("userId")long userId,
                                          @Header("sessionId")String sessionId,
                                          @Field("videoId")long videoId);

    //购买视频
    @POST("user/video/verify/v1/videoBuy")
    @FormUrlEncoded
    Observable<Result> videoBuy(@Header("userId")long userId,
                                @Header("sessionId")String sessionId,
                                @Field("videoId")long videoId,
                                @Field("price") int price
                                );

    //查询我的H币余额
    @GET("user/verify/v1/findUserWallet")
    Observable<Result<Integer>> myHB(@Header("userId")long userId,
                                     @Header("sessionId")String sessionId);

    //请求评论列表
    @GET("user/video/v1/findVideoCommentList")
    Observable<Result<List<CommentBean>>> findCommentList(@Query("videoId")long videoId);
}
