package com.wd.health.activity.shiming;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.bw.health.core.WDActivity;
import com.wd.health.mine.R;
import com.wd.health.mine.R2;
import com.wd.health.utils.getPhotoFromPhotoAlbum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindCardActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.yh_card)
    ImageView yh_card;
    @BindView(R2.id.yes)
    TextView yes;
    private Context context;
    private ImageView  iv_bank;
    private RelativeLayout rl_bk;
    /* 相机请求码 */
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int IDCARD_FRONT = 2;
    private static final int IDCARD_BACK = 3;
    private static final int BANKCARD_FRONT = 4;
    private SharedPreferences sp;




    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                switch (intent.getAction()) {
                    case "com.from.call.back.id.card.front":
                        String name = intent.getStringExtra("name");
                        String birthday = intent.getStringExtra("birthday");
                        String sex = intent.getStringExtra("sex");
                        String address = intent.getStringExtra("address");
                        String nation = intent.getStringExtra("nation");
                        String id_number = intent.getStringExtra("id_number");
                        break;
                    case "com.from.call.back.id.card.back":
                        String sign_orgin = intent.getStringExtra("sign_orgin");
                        String expiration_date = intent.getStringExtra("expiration_date");
                        break;
                    case "com.from.call.back.bank.front":
                        String bank_name = intent.getStringExtra("bank_name");
                        String card_type = intent.getStringExtra("card_type");
                        String card_number = intent.getStringExtra("card_number");
                        break;
                    default:
                        break;
                }
            }

        }
    };
    private PopupWindow popupWindow;
    private String mTempPhotoPath;
    private Uri imageUri;
    private byte[] bytes;
    private Bitmap bm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("url",mTempPhotoPath);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void initView() {
        context = this;



     getOcrSing();


    }


    /**
     * 申请SD读写权限
     */
    public void myPermission() {
        if (!getSDWritePermission()) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            Log.d("permission", "读写权限已获取,两个重要权限已获取!");
        }
    }

    private Boolean getAllPermission() {
        if (getCameraPermission() && getSDWritePermission()) {
            return true;
        }

        return false;
    }
    private Boolean getCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    private Boolean getSDWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }
    @Override
    protected void destoryData() {

    }

    public void showPop(){
        View view1 = View.inflate(this, R.layout.popwindow, null);
        popupWindow = new PopupWindow(view1,
                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(BindCardActivity.this.findViewById(R.id.aaa),
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
    private boolean isPai=false;
    //相册
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 200);
    }
    @OnClick({R2.id.back, R2.id.yh_card, R2.id.yes})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.yh_card) {
            showPop();

        } else if (i == R.id.yes) {
            Bitmap bitmap = ((BitmapDrawable)yh_card.getDrawable()).getBitmap();
            Matrix matrix = new Matrix();
            matrix.setScale(0.5f, 0.5f);
           Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                   bitmap.getHeight(), matrix, true);


            if (isPai){
                if (bitmap!=null){
                    bytes = Bitmap2Bytes(bm);
                    Intent intent = new Intent(BindCardActivity.this, NextCardActivity.class);
                    intent.putExtra("img", bytes);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(context, "请先拍照", Toast.LENGTH_SHORT).show();
            }

        } else {
        }
    }
    public byte[] Bitmap2Bytes(Bitmap bm) {
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                 return baos.toByteArray();
             }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 6;
            bm = BitmapFactory.decodeFile(mTempPhotoPath, options);
            //localre(mTempPhotoPath);
            yh_card.setImageBitmap(bm);
            isPai=true;

        } else if (requestCode == 200) {
            String realPathFromUri = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            if (realPathFromUri != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                bm = BitmapFactory.decodeFile(realPathFromUri, options);
                // localre(realPathFromUri);
                yh_card.setImageBitmap(bm);
                isPai=true;
            }
        }
        popupWindow.dismiss();
    }

    private void getOcrSing() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
                Log.i("getOcrSing", "成功:" + "," + token);
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.e("getOcrSing", "失败:" + error);
            }
        }, getApplicationContext(),"wQNqXZhaLNZxQERSiblGQ2Xc","GeIBuVjpKXIKEPGZcIHKrqt6eZpeK7HK");
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("permission", "相机权限已打开");

                myPermission();
            } else {
                Log.d("permission", "相机权限已被拒绝");
            }
        } else if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("permission", "读写权限已打开");
            } else {
                Log.i("permission", "读写权限已被拒绝");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bm=null;
        myBroadcastReceiver=null;
    }
}
