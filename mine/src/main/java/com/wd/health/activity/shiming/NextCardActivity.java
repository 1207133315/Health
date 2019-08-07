package com.wd.health.activity.shiming;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.bw.health.core.WDActivity;
import com.bw.health.util.PermissionsUtils;
import com.wd.health.R;
import com.wd.health.R2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NextCardActivity extends WDActivity {

    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.yh_card)
    ImageView yh_card;
    @BindView(R2.id.close)
    ImageView close;
    @BindView(R2.id.yes)
    TextView yes;
    private AlertDialog alertDialog;
    private Bitmap bitmap;
    String[] permissions = new String[]{Manifest.permission.VIBRATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET};
    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            //Toast.makeText(MessiageActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void forbitPermissons() {
//            finish();
            // Toast.makeText(MessiageActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_card;
    }

    @Override
    protected void initView() {
        PermissionsUtils.showSystemSetting = true;//是否支持显示系统设置权限设置窗口跳转
        //        //这里的this不是上下文，是Activity对象！
        checkPermission();
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
        final byte[] imgs = getIntent().getByteArrayExtra("img");
        if (imgs.length>0){
            bitmap = Bytes2Bimap(imgs);
            yh_card.setImageBitmap(bitmap);
        }


    }

    @Override
    protected void destoryData() {

    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    @OnClick({R2.id.back, R2.id.yh_card, R2.id.close, R2.id.yes})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.yh_card) {
        } else if (i == R.id.close) {
            yh_card.setImageResource(R.drawable.bank_card_front);
        } else if (i == R.id.yes) {
            if (bitmap!=null){
                onPreviewFrame();
                alertDialog = new ProgressDialog.Builder(this)
                        .setMessage("扫描中...")
                        .show();
            }
        } else {
        }
    }


public boolean isChoice;
    public void onPreviewFrame() {
//        Log.i("onPreviewFrame","isChoice:" + isChoice);


            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
              //  File externalFile = getExternalFilesDir("/idcard/");
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String type = "idcardBank";
                String fileName = "user.png";
                File path = new File(filePath,fileName);
                if (!path.exists()) {
                    try {
                        path.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                FileOutputStream outStream = null;

                try {
                    // 打开指定文件对应的输出流
                    outStream = new FileOutputStream(path);
                        // 把位图输出到指定文件中
                        bitmap.compress(Bitmap.CompressFormat.PNG,
                                100, outStream);
                        outStream.close();
                    //解析银行卡图片
                    BankCardParams param = new BankCardParams();
                    param.setImageFile(path);
                    // 调用银行卡识别服务
                    OCR.getInstance().recognizeBankCard(param, new OnResultListener<BankCardResult>() {
                        @Override
                        public void onResult(BankCardResult result) {
                            if (result != null) {
                                String type = null;
                                String bankCardNumber = null;
                                String bankName = null;

                                if (result.getBankCardType() == BankCardResult.BankCardType.Credit) {
                                    type = "信用卡";
                                } else if (result.getBankCardType() == BankCardResult.BankCardType.Debit) {
                                    type = "借记卡";
                                } else {
                                    type = "不能识别";
                                }

                                if (!TextUtils.isEmpty(result.getBankCardNumber())) {
                                    bankCardNumber = result.getBankCardNumber();
                                }

                                if (!TextUtils.isEmpty(result.getBankName())) {
                                    bankName = result.getBankName();
                                }

                                if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(bankCardNumber) &&
                                        !TextUtils.isEmpty(bankName)) {
                                    alertDialog.dismiss();
                                    Intent intent = new Intent(NextCardActivity.this, BandShowActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("type", type);
                                    bundle.putString("bankCardNumber", bankCardNumber);
                                    bundle.putString("bankName", bankName);
                                    bundle.putString("img_path", path.getAbsolutePath());
                                    intent.putExtra("bundle",bundle);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    isChoice = true;
                                }

                            } else {
                                isChoice = true;
                            }
                        }

                        @Override
                        public void onError(OCRError error) {
                           // isChoice = true;
                            Log.d("MainActivity", "onError: " + error.getMessage());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("aa", "onPreviewFrame: "+e);
                }


            } else {
                Toast.makeText(this, "没有检测到内存卡", Toast.LENGTH_SHORT).show();
            }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();

        }
    }
    }
