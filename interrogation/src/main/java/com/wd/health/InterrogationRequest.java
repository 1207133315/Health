package com.wd.health;


import com.bw.health.bean.Result;
import com.wd.health.bean.DepartmentBean;
import com.wd.health.bean.Doctor;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Observable<Result<List<Doctor>>>findDoctorList(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("deptId") int deptId,
                                                   @Query("condition") int condition,
                                                   @Query("sortBy") int sortBy,
                                                   @Query("page") int page,
                                                   @Query("count") int count);
}
