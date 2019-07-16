package com.bw.health;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Irequset {
    /*刘浩*/
    // 发送邮箱验证码
    @POST("user/v1/sendOutEmailCode")
    Observable<Result> sendEmailCode(@Query("email") String email);

    //注册
    @POST("user/v1/register")
    Observable<Result>register(@Query("email")String email,
                               @Query("code")String code,
                               @Query("pwd1")String pwd1,
                               @Query("pwd2")String pwd2,
                               @Query("invitationCode")String invitationCode);
    //登陆
    @POST("user/v1/login")
    Observable<Result<LoginBean>>login(@Query("email")String email,
                                       @Query("pwd")String pwd);

    //修改密码
    @PUT("user/verify/v1/updateUserPwd")
    Observable<Result>updateUserPwd(@Header("userId")int userId,
                                    @Header("sessionId")String sessionId,
                                    @Query("oldPwd")String oldPwd,
                                    @Query("newPwd")String newPwd);

    /*刘浩*/
}
