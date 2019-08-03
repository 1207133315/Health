package com.wd.health;

import com.bw.health.bean.MationBean;
import com.bw.health.bean.Result;
import com.wd.health.bean.BeAdoptedBean;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.CollectCircleBean;
import com.wd.health.bean.CollectVideoBean;
import com.wd.health.bean.DoctorBean;
import com.wd.health.bean.MySickCircleCommentListBean;
import com.wd.health.bean.PatientsCircleBean;
import com.wd.health.bean.RecordListBean;
import com.wd.health.bean.UserArchivesBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Observable<Result<List<DoctorBean>>> findUserDoctorFollowList(@Header("userId") int userId,
                                                                  @Header("sessionId") String sessionId,
                                                                  @Query("page") int page,
                                                                  @Query("count") int count);

    //查询用户收藏资讯列表

    @GET("user/verify/v1/findUserInfoCollectionList")
    Observable<Result<List<MationBean>>> findZiXun(@Header("userId") long userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("page") int page,
                                                   @Query("count") int count
    );

    //查询用户收藏病友圈列表
    @GET("user/verify/v1/findUserSickCollectionList")
    Observable<Result<List<CollectCircleBean>>> findCircle(@Header("userId") long userId,
                                                           @Header("sessionId") String sessionId,
                                                           @Query("page") int page,
                                                           @Query("count") int count);

    //查询用户收藏健康讲堂列表
    @GET("user/verify/v1/findVideoCollectionList")
    Observable<Result<List<CollectVideoBean>>> findVideo(@Header("userId") long userId,
                                                         @Header("sessionId") String sessionId,
                                                         @Query("page") int page,
                                                         @Query("count") int count);

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

    //查询消费记录
    @GET("user/verify/v1/findUserConsumptionRecordList")
    Observable<Result<List<RecordListBean>>> findRecordList(@Header("userId") long userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page") int page,
                                                            @Query("count") int count);

    //查询我的H币余额
    @GET("user/verify/v1/findUserWallet")
    Observable<Result<Integer>> myHB(@Header("userId") long userId,
                                     @Header("sessionId") String sessionId);

    //取消关注医生
    @DELETE("user/inquiry/verify/v1/cancelFollow")
    Observable<Result> cancelFollow(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("doctorId") int doctorId);

    //充值
    @POST("user/verify/v1/recharge")
    @FormUrlEncoded
    Observable<Result<String>> add(@Header("userId") long userId,
                                   @Header("sessionId") String sessionId,
                                   @Field("money") int money,
                                   @Field("payType") int payType
    );

    //用户购买视频列表
    @GET("user/verify/v1/findUserVideoBuyList")
    Observable<Result<List<CollectVideoBean>>> buyVideoList(@Header("userId") long userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page") int page,
                                                            @Query("count") int count);

    //删除用户购买视频
    @HTTP(method = "DELETE", path = "user/verify/v1/deleteVideoBuy", hasBody = true)
    Observable<Result> deleteVideoBuy(@Header("userId") long userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("videoId") long videoId);

    //查询用户档案
    @GET("user/verify/v1/findUserArchives")
    Observable<Result<UserArchivesBean>> userArchives(@Header("userId") long userId,
                                                      @Header("sessionId") String sessionId);

    //修改用户昵称
    @PUT("user/verify/v1/modifyNickName")
    Observable<Result> modifyNickName(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("nickName") String nickName);

    //修改用户性别
    @PUT("user/verify/v1/updateUserSex")
    Observable<Result> updateUserSex(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("sex") int sex);


    /***李宁康---------------------------------------------**/
    //上传档案
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("user/verify/v1/addUserArchives")
    Observable<Result> upArchives(@Header("userId") long userId,
                                  @Header("sessionId") String sessionId,
                                  @Body RequestBody body);

    //上传用户档案相关图片
    @POST("user/verify/v1/uploadArchivesPicture")
    Observable<Result> upArchivesPic(@Header("userId") long userId,
                                     @Header("sessionId") String sessionId,
                                     @Body MultipartBody body
    );

    //删除档案
    @HTTP(method = "DELETE", path = "user/verify/v1/deleteUserArchives", hasBody = true)
    Observable<Result> deleteArchives(@Header("userId") long userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("archivesId") long archivesId
    );

    //修改档案
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("user/verify/v1/updateUserArchives")
    Observable<Result> updateArchives(@Header("userId") long userId,
                                      @Header("sessionId") String sessionId,
                                      @Body RequestBody body);
    /***李宁康---------------------------------------------**/


    /***刘浩---------------------------------------------**/
    //完善用户信息
    @PUT("user/verify/v1/perfectUserInfo")
    Observable<Result> perfectUserInfo(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Query("age") int age,
                                       @Query("height") int height,
                                       @Query("weight") int weight);

    /***刘浩---------------------------------------------**/


    /***郭亚杰---------------------------------------------**/

    //查看我的病友圈发帖列表
    @GET("user/sickCircle/verify/v1/findMySickCircleList")
    Observable<Result<List<PatientsCircleBean>>> findMySickCircleList(@Header("userId") String userId,
                                                                      @Header("sessionId") String sessionId,
                                                                      @Query("page") String page,
                                                                      @Query("count") String count);


    //查看病友圈评论列表
    @GET("user/sickCircle/v1/findSickCircleCommentList")
    Observable<Result<List<CircleCommentListBean>>> circlecomment(@Query("sickCircleId") String sickCircleId,
                                                                  @Query("page") String page,
                                                                  @Query("count") String count);


    //6. 采纳病友圈优秀的评论
    @PUT("user/sickCircle/verify/v1/adoptionProposal")
    Observable<Result> adoptionProposal(@Header("userId") String userId,
                                        @Header("sessionId") String sessionId,
                                        @Query("commentId") String commentId,
                                        @Query("sickCircleId") String sickCircleId);


    //查询我的病友圈帖子的评论列表
    @GET("user/sickCircle/verify/v1/findMySickCircleCommentList")
    Observable<Result<MySickCircleCommentListBean>> findMySickCircleCommentList(@Header("userId") String userId,
                                                                               @Header("sessionId") String sessionId,
                                                                               @Query("sickCircleId") String sickCircleId,
                                                                               @Query("page") String page,
                                                                               @Query("count") String count);




    //查询我的被采纳的建议
    @GET("user/verify/v1/findMyAdoptedCommentList")
    Observable<Result<List<BeAdoptedBean>>> findMyAdoptedCommentList(@Header("userId") String userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") String page,
                                                               @Query("count") String count);

    /***郭亚杰---------------------------------------------**/

}
