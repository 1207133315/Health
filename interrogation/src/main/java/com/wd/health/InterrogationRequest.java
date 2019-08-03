package com.wd.health;


import com.bw.health.bean.Result;
import com.wd.health.activity.bean.UserRecordBean;
import com.wd.health.bean.DepartmentBean;
import com.wd.health.bean.Doctor;
import com.wd.health.bean.DoctordetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * com.bw.health
 *
 * @author 李宁康
 * @date 2019 2019/07/14 15:52
 */
public interface InterrogationRequest {
    //查询科室
    @GET("share/knowledgeBase/v1/findDepartment")
    Observable<Result<List<DepartmentBean>>> findDepartment();

    //查询问诊医生列表
    @GET("user/inquiry/v1/findDoctorList")
    Observable<Result<List<Doctor>>> findDoctorList(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Query("deptId") int deptId,
                                                    @Query("condition") int condition,
                                                    @Query("sortBy") String sortBy,
                                                    @Query("page") int page,
                                                    @Query("count") int count);

    //查询问诊医生列表2
    @GET("user/inquiry/v1/findDoctorList")
    Observable<Result<List<Doctor>>> findDoctorList2(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("deptId") int deptId,
                                                     @Query("condition") int condition,
                                                     @Query("page") int page,
                                                     @Query("count") int count);

    //查询医生明细信息
    @GET("user/inquiry/v1/findDoctorInfo")
    Observable<Result<DoctordetailBean>> findDoctorInfo(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("doctorId") int doctorId);

    //关注医生
    @POST("user/inquiry/verify/v1/followDoctor")
    Observable<Result> followDoctor(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("doctorId") int doctorId);
    //取消关注医生
    @DELETE("user/inquiry/verify/v1/cancelFollow")
    Observable<Result> cancelFollow(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("doctorId") int doctorId);
    //咨询医生
    @PUT("user/inquiry/verify/v1/consultDoctor")
    Observable<Result<String>> consultDoctor(@Header("userId")long userId,
                                             @Header("sessionId")String sessionId,
                                             @Query("doctorId")int doctorId);

   
}
