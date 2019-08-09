package com.wd.health.activity.shiming;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.core.WDActivity;
import com.bw.health.util.PermissionsUtils;


import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.activity.MineMessageActivity;
import com.wd.health.utils.getPhotoFromPhotoAlbum;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ShiMingActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.front)
    ImageView front;
    @BindView(R2.id.contrary)
    ImageView contrary;
    @BindView(R2.id.yes)
    TextView yes;
    private PopupWindow popupWindow;
    private String mTempPhotoPath;
    private Uri imageUri;
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
    private Bitmap bm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shi_ming;
    }

    @Override
    protected void initView() {
        PermissionsUtils.showSystemSetting = true;//是否支持显示系统设置权限设置窗口跳转
        //        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    protected void destoryData() {

    }

    public void showPop(){
        View view1 = View.inflate(this, R.layout.popwindow, null);
        popupWindow = new PopupWindow(view1,
                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(ShiMingActivity.this.findViewById(R.id.aaa),
                Gravity.CENTER, 0, 0);
        Button viewById = view1.findViewById(R.id.quxiao);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        TextView paizhao = view1.findViewById(R.id.paizhao);
        TextView xiangce = view1.findViewById(R.id.xiangce);
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
    }


    //相机
    private void takePhoto() {
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        mTempPhotoPath = Environment.getExternalStorageDirectory() + "/photo.png";
        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
        imageUri = Uri.fromFile(new File(mTempPhotoPath));
        /*imageUri = FileProvider.getUriForFile(MessiageActivity.this,
                MessiageActivity.this.getApplicationContext().getPackageName() +".my.provider",
                new File(mTempPhotoPath));*/
        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, 100);
    }

    //相册
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 200);
    }
    public  boolean isFront;
    @OnClick({R2.id.back, R2.id.front, R2.id.contrary, R2.id.yes})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
        } else if (i == R.id.front) {
            isFront=true;
            showPop();
        } else if (i == R.id.contrary) {
            isFront=false;
        } else if (i == R.id.yes) {
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
             bm = BitmapFactory.decodeFile(mTempPhotoPath, options);
            //localre(mTempPhotoPath);
            if (isFront){
                front.setImageBitmap(bm);
            }else {
                contrary.setImageBitmap(bm);
            }

        } else if (requestCode == 200) {
            String realPathFromUri = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            if (realPathFromUri != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bm = BitmapFactory.decodeFile(realPathFromUri, options);
               // localre(realPathFromUri);
                if (isFront){
                    front.setImageBitmap(bm);
                }else {
                    contrary.setImageBitmap(bm);
                }
            }
        }
    }
    //训练数据路径，tessdata
    static final String TESSBASE_PATH = Environment.getExternalStorageDirectory() + "/tesseract/";
    //识别语言英文
    static final String DEFAULT_LANGUAGE = "eng";
    //识别语言简体中文
    static final String CHINESE_LANGUAGE = "chi_sim";


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bm=null;
        permissions=null;
        permissionsResult=null;
    }
}
