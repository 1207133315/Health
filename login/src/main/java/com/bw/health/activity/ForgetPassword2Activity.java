package com.bw.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bw.health.core.WDActivity;
import com.bw.login.R;
import com.bw.login.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPassword2Activity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.pwd)
    EditText pwd;
    @BindView(R2.id.pwd1eye)
    ImageView pwd1eye;
    @BindView(R2.id.pwd2)
    EditText pwd2;
    @BindView(R2.id.pwd2eye)
    ImageView pwd2eye;
    @BindView(R2.id.xiyibu)
    Button xiyibu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password2;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String yzm = intent.getStringExtra("yzm");
    }

    @Override
    protected void destoryData() {

    }


    @OnClick({R2.id.back, R2.id.pwd1eye, R2.id.pwd2eye, R2.id.xiyibu})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.pwd1eye) {
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
        } else if (i == R.id.xiyibu) {

        }
    }
}
