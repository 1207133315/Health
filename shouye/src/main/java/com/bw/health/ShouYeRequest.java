package com.bw.health;

import com.bw.health.bean.BingZhengBean;
import com.bw.health.bean.DepartmentBean;
import com.bw.health.bean.MationDetail;
import com.bw.health.bean.Result;
import com.bw.health.bean.YaoPinBean;
import com.bw.health.bean.YaoPinTwoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * com.bw.health
 *
 * @author 李宁康
 * @date 2019 2019/07/14 15:52
 */
public interface ShouYeRequest {
    //查询科室
    @GET("share/knowledgeBase/v1/findDepartment")
    Observable<Result<List<DepartmentBean>>> findDepartment();
//根据科室查询对应病症
    @GET("share/knowledgeBase/v1/findDiseaseCategory")
    Observable<Result<List<BingZhengBean>>> findDiseaseCategory(@Query("departmentId") long departmentIddepartmentId);

    //药品科目分类列表查询
   @GET("share/knowledgeBase/v1/findDrugsCategoryList")
    Observable<Result<List<YaoPinBean>>> findDrugsCategoryList();

   //根据药品类目查询常见药品
    @GET("share/knowledgeBase/v1/findDrugsKnowledgeList")
    Observable<Result<List<YaoPinTwoBean>>> findDrugsKnowledgeList(@Query("drugsCategoryId")long drugsCategoryId,
                                                                   @Query("page")int page,
                                                                   @Query("count")int count);
    //查询资讯详情
    @GET("share/information/v1/findInformation")
    Observable<Result<MationDetail>> findmationDetail(@Header("userId")long userId,
                                                          @Header("sessionId")String sessionId,
                                                          @Query("infoId")long infoId);
    @POST("user/verify/v1/watchInfoRewards")
    @FormUrlEncoded
    Observable<Result> watchInfoRewards(@Header("userId")long userId,
                                        @Header("sessionId")String sessionId,
                                        @Field("infoId") long infoId);

    @POST("user/verify/v1/addInfoCollection")
    @FormUrlEncoded
    Observable<Result> addInfo(@Header("userId")long userId,
                                        @Header("sessionId")String sessionId,
                                        @Field("infoId") long infoId);
}
