package com.bw.health.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.prenster.SendOutEmailCodePresenter;
import com.wd.health.R;
import com.wd.health.R2;


import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends WDActivity {

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
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.email)
    EditText email;
    @BindView(R2.id.hqyzm)
    Button hqyzm;
    @BindView(R2.id.yzm)
    EditText yzm;
    @BindView(R2.id.xiyibu)
    Button xiyibu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        sendOutEmailCodePresenter = new SendOutEmailCodePresenter(new SendOutEmailCode());
    }

    @Override
    protected void destoryData() {

    }

    //发送验证码
    public class SendOutEmailCode implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            hqyzm.setTextSize(7);
            handler.sendEmptyMessageDelayed(1,1000);
        }

        @Override
        public void fail(ApiException data, Object... args) {

            Toast.makeText(ForgetPasswordActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick({R2.id.back, R2.id.hqyzm, R2.id.xiyibu})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.hqyzm) {
            if (email.getText().toString().trim()!=null){
                sendOutEmailCodePresenter.reqeust((String)email.getText().toString().trim());
            }else {
                Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.xiyibu) {
            if (TextUtils.isEmpty(email.getText())||TextUtils.isEmpty(yzm.getText())){
                Toast.makeText(this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent(ForgetPasswordActivity.this,ForgetPassword2Activity.class);
                intent.putExtra("email",email.getText().toString().trim());
                intent.putExtra("yzm",yzm.getText().toString().trim());
                startActivity(intent);
            }
        }
    }
}
