package com.wd.health.model;

import com.bw.health.bean.Result;
import com.wd.health.bean.CircleBingZhengBean;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.CircleInfoBean;
import com.wd.health.bean.CircleSearchBean;
import com.wd.health.bean.CircleUserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CircleIRquest {

    //-------------病友圈接口---请勿修改-----------
    //2.查询病友圈详情
    @GET("user/sickCircle/v1/findSickCircleInfo")
    Observable<Result<CircleInfoBean>> findSickCircleInfo(@Query("sickCircleId") String sickCircleId);

    //收藏 病友圈
    @POST("user/verify/v1/addUserSickCollection")
    Observable<Result> addUserSickCollection(@Header("userId") String userId,
                                             @Header("sessionId") String sessionId,
                                             @Query("sickCircleId") String sickCircleId);

    //取消 收藏病友圈
    // @HTTP(method = "DELETE",path = "user/verify/v1/cancelSickCollection",hasBody = true)
    @DELETE("user/verify/v1/cancelSickCollection")
    Observable<Result> cancelSickCollection(@Header("userId") String userId,
                                            @Header("sessionId") String sessionId,
                                            @Query("sickCircleId") String sickCircleId);


    // 3.根据关键词查询病友圈
    @GET("user/sickCircle/v1/searchSickCircle")
    Observable<Result<List<CircleSearchBean>>> searchSickCircle(
            @Query("keyWord") String keyWord);

    //4.病友圈发表评论
    @POST("user/sickCircle/verify/v1/publishComment")
    Observable<Result> publishComment(@Header("userId") String userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("sickCircleId") String sickCircleId,
                                      @Query("content") String content);


    //5. 查询病友圈评论列表
    @GET("user/sickCircle/v1/findSickCircleCommentList")
    Observable<Result<List<CircleCommentListBean>>> findSickCircleCommentList(
            @Query("sickCircleId") String sickCircleId,
            @Query("page") String page,
            @Query("count") String count);


    //6. 采纳病友圈优秀的评论
    @PUT("user/sickCircle/verify/v1/adoptionProposal")
    Observable<Result> cainapinglun(@Header("userId") String userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("commentId") String commentId,
                                    @Query("sickCircleId") String sickCircleId);


    //7.发表支持的观点
    @PUT("user/sickCircle/verify/v1/expressOpinion")
    Observable<Result> expressOpinion(@Header("userId") String userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("commentId") String commentId,
                                      @Query("opinion") String opinion);

    //8.查看病友的病友圈发帖列表
    @GET("user/sickCircle/v1/findPatientSickCircleList")
    Observable<Result<List<CircleUserInfoBean>>> userInfo(@Query("patientUserId") String patientUserId,
                                                          @Query("page") String page,
                                                          @Query("count") String count);


    // 根据科室查询对应病症
    @GET("share/knowledgeBase/v1/findDiseaseCategory")
    Observable<Result<List<CircleBingZhengBean>>> bingzheng(@Query("departmentId") String departmentId);


    //11. 发布病友圈
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("user/sickCircle/verify/v1/publishSickCircle")
    Observable<Result<Integer>> publishSickCircle(@Header("userId") String userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Body RequestBody body);


    //12.上传用户病友圈相关图片
//    @Multipart    //添加这一行即可
    @POST("user/sickCircle/verify/v1/uploadSickCirclePicture")
    Observable<Result> SctxId(@Header("userId") String userId,
                              @Header("sessionId") String sessionId,
                              @Query("sickCircleId") String sickCircleId,
                              @Body MultipartBody body);

    //-------------病友圈接口---请勿修改-----------

    //刘浩
    //做任务
    @POST("user/verify/v1/doTask")
    Observable<Result> doTask(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Query("taskId") int taskId);
    //刘浩
}
