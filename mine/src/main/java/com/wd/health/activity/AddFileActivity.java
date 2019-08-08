package com.wd.health.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.bw.health.util.GlideLoader;
import com.bw.health.util.PermissionsUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.wd.health.R;
import com.wd.health.presenter.UpArchivesPicPresenter;
import com.wd.health.presenter.UpArchivesPresenter;
import com.wd.health.utils.getPhotoFromPhotoAlbum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddFileActivity extends WDActivity implements View.OnClickListener {


    private ImageView back;
    /**
     * 请输入您的主要症状
     */
    private EditText bingzheng;
    /**
     * 请输入您的详细病史
     */
    private EditText bingshi;
    /**
     * 请输入您的既往病史
     */
    private EditText jBingshi;
    /**
     * 请选择治疗开始时间
     */
    private EditText start_time;
    private ImageView select_time;
    /**
     * 请选择治疗结束时间
     */
    private EditText end_time;
    private EditText jingli;
    private EditText guocheng;
    private ImageView select_time2;
    private ImageView image;
    private RelativeLayout ll;

    private LinearLayout layout_main;
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
    private Button yes;
    private Gson gson;
    private LoginBean loginBean;
    private UpArchivesPresenter upArchivesPresenter;
    private UpArchivesPicPresenter upArchivesPicPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_file;
    }

    @Override
    protected void initView() {
        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
        loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
        upArchivesPresenter = new UpArchivesPresenter(new UpArchives());
        upArchivesPicPresenter = new UpArchivesPicPresenter(new UpArchivesPic());
        back = (ImageView) findViewById(R.id.back);
        layout_main=findViewById(R.id.layout_main);
        yes = findViewById(R.id.yes);
        back.setOnClickListener(this);
        bingzheng = (EditText) findViewById(R.id.bingzheng);
        gson = new Gson();
        jingli = (EditText) findViewById(R.id.jingli);
        guocheng = (EditText) findViewById(R.id.guocheng);
        bingshi = (EditText) findViewById(R.id.bingshi);
        jBingshi = (EditText) findViewById(R.id.jBingshi);
        start_time = (EditText) findViewById(R.id.start_time);
        select_time = (ImageView) findViewById(R.id.select_time);
        select_time.setOnClickListener(this);
        end_time = (EditText) findViewById(R.id.end_time);
        select_time2 = (ImageView) findViewById(R.id.select_time2);
        select_time2.setOnClickListener(this);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);
        ll = (RelativeLayout) findViewById(R.id.ll);
        ll.setOnClickListener(this);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paths.size()>=6){
                    Snackbar.make(layout_main,"图片数量不可超过6张",BaseTransientBottomBar.LENGTH_LONG).show();
                }else {
                   getImg();
                }

            }
        });
        setTime();
        fabiao();
    }
    Map<String,String> map=new HashMap<>();
    private void fabiao() {
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                 String bingshi = AddFileActivity.this.bingshi.getText().toString();
                 String bingzheng = AddFileActivity.this.bingzheng.getText().toString();
                 String jBingshi = AddFileActivity.this.jBingshi.getText().toString();
                 String jingli = AddFileActivity.this.jingli.getText().toString();
                 String guocheng = AddFileActivity.this.guocheng.getText().toString();
                 String start_time = AddFileActivity.this.start_time.getText().toString();
                 String end_time = AddFileActivity.this.end_time.getText().toString();
                 map.put("diseaseMain",bingzheng);
                 map.put("diseaseNow",bingshi);
                 map.put("diseaseBefore",jBingshi);
                 map.put("treatmentHospitalRecent",jingli);
                 map.put("treatmentProcess",guocheng);
                 map.put("treatmentStartTime",start_time);
                 map.put("treatmentEndTime",end_time);
                 String s = gson.toJson(map);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);

                if (files.size()>0){
                    upArchivesPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),body);
                    upArchivesPicPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),files);
                }else {
                    upArchivesPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),body);
                }

            }
        });
    }

    public class UpArchives implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {

            Snackbar.make(layout_main,data.getMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Snackbar.make(layout_main,data.getDisplayMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    public class UpArchivesPic implements DataCall<Result>{
        @Override
        public void success(Result data, Object... args) {
            Snackbar.make(layout_main,data.getMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Snackbar.make(layout_main, data.getDisplayMessage(),BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }
    private void setTime() {
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                 pvTime = new TimePickerView.Builder(AddFileActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        start_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{true,true,true,false,false,false})
                         .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();

            }
        });

        select_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime = new TimePickerView.Builder(AddFileActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        end_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{true,true,true,false,false,false})
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });
    }
    private TimePickerView pvTime;
    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private ArrayList<String> paths=new ArrayList<>();
    private ImageConfig imageConfig;
    public void getImg(){
        imageConfig = new ImageConfig.Builder(
                new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                .titleBgColor(getResources().getColor(R.color.titleBlue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(6)
                // 设置图片显示容器，参数：（1、显示容器，2、每行显示数量（建议不要超过8个），是否可删除）
                .setContainer(ll, 3, true)
                // 已选择的图片路径
                .pathList(paths)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/temp")
                // 开启拍照功能 （默认关闭）
                .showCamera()
                .requestCode(200)
                .build();
        ImageSelector.open(AddFileActivity.this, imageConfig);
    }
    @Override
    protected void destoryData() {

    }

    private List<File> files=new ArrayList<>();
    String realPathFromUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 200:

                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    paths.clear();
                    paths.addAll(pathList);

                        for (int i = 0; i < paths.size(); i++) {
                            FileInputStream fs = null;
                            try {
                                fs = new FileInputStream(paths.get(i));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
                            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap , null,null));
                            realPathFromUri = getPhotoFromPhotoAlbum.getRealPathFromUri(this, imageUri);
                            if (realPathFromUri!=null){
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 4;
                                Bitmap bm = BitmapFactory.decodeFile(realPathFromUri, options);
                                File file = getFile(bm);
                                files.add(file);
                            }
                        }


                    break;
            }
        } else {
            Toast.makeText(AddFileActivity.this, "data为空 ！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.select_time) {
        } else if (i == R.id.select_time2) {
        }  else if (i == R.id.ll) {
        } else {
        }
    }

    //在这里抽取了一个方法   可以封装到自己的工具类中...
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        permissions=null;
        permissionsResult=null;
    }
}
