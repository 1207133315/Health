package com.bw.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.prenster.RegisterPresenter;
import com.bw.health.prenster.SendOutEmailCodePresenter;
import com.bw.health.util.RsaCoder;
import com.wd.health.R;
import com.wd.health.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/RegisterActivity/")
public class RegisterActivity extends WDActivity {


    @BindView(R2.id.logo)
    ImageView logo;
    @BindView(R2.id.liner)
    LinearLayout liner;
    @BindView(R2.id.email)
    EditText email;
    @BindView(R2.id.yzm)
    EditText yzm;
    @BindView(R2.id.pwd)
    EditText pwd;
    @BindView(R2.id.pwd1eye)
    ImageView pwd1eye;
    @BindView(R2.id.pwd2)
    EditText pwd2;
    @BindView(R2.id.pwd2eye)
    ImageView pwd2eye;
    @BindView(R2.id.yqm)
    EditText yqm;
    @BindView(R2.id.register)
    Button register;
    @BindView(R2.id.hqyzm)
    Button hqyzm;

    int i=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1){
                hqyzm.setText("("+i+"s)后重新获取");
                i--;
                handler.sendEmptyMessageDelayed(1,1000);
                if (i==0){
                    hqyzm.setTextSize(10);
                    hqyzm.setText("获取验证码");
                    handler.removeMessages(1);
                    i=60;
                }
            }
        }
    };
    private SendOutEmailCodePresenter sendOutEmailCodePresenter;
    private RegisterPresenter registerPresenter;
    private String s;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        sendOutEmailCodePresenter = new SendOutEmailCodePresenter(new SendOutEmailCode());
        registerPresenter = new RegisterPresenter(new Register());
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.pwd1eye, R2.id.pwd2eye, R2.id.register,R2.id.hqyzm})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.pwd1eye) {
            if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == pwd.getInputType()) {
                //如果不可见就设置为可见
                pwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                pwd1eye.setImageResource(R.mipmap.login_icon_show_password);

            } else {
                //如果可见就设置为不可见
                pwd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                pwd1eye.setImageResource(R.mipmap.login_icon_hide_password_n);
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            //执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
            pwd.setSelection(pwd.getText().toString().length());
        } else if (i == R.id.pwd2eye) {
            if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == pwd2.getInputType()) {
                //如果不可见就设置为可见
                pwd2.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                pwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                pwd2eye.setImageResource(R.mipmap.login_icon_show_password);

            } else {
                //如果可见就设置为不可见
                pwd2.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                pwd2eye.setImageResource(R.mipmap.login_icon_hide_password_n);
                pwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            //执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
            pwd2.setSelection(pwd2.getText().toString().length());
        } else if (i == R.id.register) {
            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(yzm.getText().toString()) || TextUtils.isEmpty(pwd.getText().toString()) || TextUtils.isEmpty(pwd2.getText().toString())) {
                Toast.makeText(this, "必填信息不能为空", Toast.LENGTH_SHORT).show();
            }else {
                //注册
                try {
                    s = RsaCoder.encryptByPublicKey(pwd.getText().toString().trim());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (pwd.getText().toString().trim().equals(pwd2.getText().toString().trim())){
                    registerPresenter.reqeust(email.getText().toString().trim(),yzm.getText().toString().trim(),s,s,yqm.getText().toString().trim());
                }else {
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }

            }
        }else if (i == R.id.hqyzm){
            if (email.getText().toString().trim()!=null){

                sendOutEmailCodePresenter.reqeust((String)email.getText().toString().trim());
            }else {
                Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //发送验证码
    public class SendOutEmailCode implements DataCall{

        @Override
        public void success(Object data, Object... args) {
                hqyzm.setTextSize(7);
                handler.sendEmptyMessageDelayed(1,1000);
        }

        @Override
        public void fail(ApiException data, Object... args) {

            Toast.makeText(RegisterActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //注册
    public class Register implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            intent.putExtra("email",email.getText().toString().trim());
            intent.putExtra("pwd",pwd.getText().toString().trim());
            startActivity(intent);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(RegisterActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
