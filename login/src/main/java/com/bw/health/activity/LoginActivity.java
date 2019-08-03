package com.bw.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.core.WDApplication;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.prenster.LoginPresenter;
import com.bw.health.util.RsaCoder;
import com.wd.health.R;
import com.wd.health.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/LoginActivity/")
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

@Route(path ="/LoginActivity/")
public class LoginActivity extends WDActivity {


    @BindView(R2.id.logo)
    ImageView logo;
    @BindView(R2.id.liner)
    LinearLayout liner;
    @BindView(R2.id.email)
    EditText email;
    @BindView(R2.id.pwd)
    EditText pwd;
    @BindView(R2.id.login1)
    Button login;
    @BindView(R2.id.wjmm)
    TextView wjmm;
    @BindView(R2.id.ljzc)
    TextView ljzc;
    @BindView(R2.id.qtdlfs)
    TextView qtdlfs;
    @BindView(R2.id.eye)
    ImageView eye;
    String s = null;
    @BindView(R2.id.wxlogin)
    ImageView wxlogin;
    private LoginPresenter loginPresenter;
    RelativeLayout main_layout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(new Login());
        Intent intent = getIntent();
       main_layout=findViewById(R.id.main_layout);
        if (intent!=null){
            String email1 = intent.getStringExtra("email");
            String pwdd = intent.getStringExtra("pwd");
            email.setText(email1);
            pwd.setText(pwdd);
        }
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.login1, R2.id.wjmm, R2.id.ljzc, R2.id.eye,R2.id.wxlogin})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.login1) {
            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(pwd.getText().toString())) {
                Toast.makeText(this, "输入信息不能为空", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    s = RsaCoder.encryptByPublicKey(pwd.getText().toString().trim());
                    Log.d("LoginActivityw", s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loginPresenter.reqeust(email.getText().toString().trim(), s);
            }
        } else if (i == R.id.wjmm) {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        } else if (i == R.id.ljzc) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (i == R.id.eye) {
            if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == pwd.getInputType()) {
                //如果不可见就设置为可见
                pwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eye.setImageResource(R.mipmap.login_icon_hide_password_n);

            } else {
                //如果可见就设置为不可见
                pwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye.setImageResource(R.mipmap.login_icon_show_password);
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            //执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
            pwd.setSelection(pwd.getText().toString().length());
        }else if (i == R.id.wxlogin){

        }
    }


    public class Login implements DataCall {

        private String s1;

        @Override
        public void success(Object data, Object... args) {
            Result<LoginBean> result = (Result<LoginBean>) data;
            LoginBean result1 = result.getResult();
            result1.setIslogin(true);
            result1.setPwd(s);
            Log.d("Login4", result1.getSessionId());
            LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
            loginBeanDao.deleteAll();
            loginBeanDao.insertOrReplace(result1);
            UpToken.getUpToken().addToken(WDApplication.getRegistrationID());
            try {
                 String s = RsaCoder.decryptByPublicKey(result1.getJiGuangPwd());
                s1 = MD5Utils.MD5(s);
                JMessageClient.login(result1.getUserName(), s1, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        switch (i){
                            case 801003:
                                Snackbar.make(main_layout,"用户名不存在",BaseTransientBottomBar.LENGTH_LONG).show();
                                break;
                            case 871301:
                                Snackbar.make(main_layout,"密码格式错误",BaseTransientBottomBar.LENGTH_LONG).show();
                                break;
                            case 801004:
                                Snackbar.make(main_layout,"密码错误",BaseTransientBottomBar.LENGTH_LONG).show();
                                break;
                            case 0:
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Snackbar.make(main_layout,"登录失败",BaseTransientBottomBar.LENGTH_LONG).show();
                                break;
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(LoginActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
