package com.wd.health.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.DateUtils;
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
import com.wd.health.adapter.ImageAdapter;
import com.wd.health.bean.UserArchivesBean;
import com.wd.health.presenter.UpArchivesPicPresenter;
import com.wd.health.presenter.UpdateArchivesPresenter;
import com.wd.health.presenter.UserArchivesPresenter;
import com.wd.health.utils.getPhotoFromPhotoAlbum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateFileActivity extends WDActivity implements View.OnClickListener {
    private UserArchivesPresenter userArchivesPresenter;
    private ImageView back;
    private EditText bingzheng;
    private EditText bingshi;
    private EditText jBingshi;
    private EditText jingli;
    private EditText start_time;
    private EditText end_time;
    private EditText guocheng;

    private RelativeLayout ll;
    /**
     * 保存
     */
    private Button yes;
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
    private ImageAdapter imageAdapter;
    private Gson gson;
    private UpArchivesPicPresenter upArchivesPicPresenter;
    private UpdateArchivesPresenter updateArchivesPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_file;
    }
     String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private RecyclerView recyclerView;
    private int i=6;
    @Override
    protected void initView() {
        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
        Resources res = this.getResources();
        BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.add);
        Bitmap img = d.getBitmap();

        String fn = "add.png";
         File file = new File(path , fn);
        try {
            file.createNewFile(); //创建文件
            OutputStream os = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


recyclerView=findViewById(R.id.recycler);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        bingzheng = (EditText) findViewById(R.id.bingzheng);
        bingshi = (EditText) findViewById(R.id.bingshi);
        jBingshi = (EditText) findViewById(R.id.jBingshi);
        jingli = (EditText) findViewById(R.id.jingli);
        start_time = (EditText) findViewById(R.id.start_time);
        end_time = (EditText) findViewById(R.id.end_time);
        guocheng = (EditText) findViewById(R.id.guocheng);


        ll = (RelativeLayout) findViewById(R.id.ll);
        yes = (Button) findViewById(R.id.yes);
        yes.setOnClickListener(this);
        layout_main = (LinearLayout) findViewById(R.id.layout_main);
        userArchivesPresenter = new UserArchivesPresenter(new UserArchives());
        final LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);
        userArchivesPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());
        recyclerView.setLayoutManager(new GridLayoutManager(UpdateFileActivity.this,3));
        imageAdapter = new ImageAdapter(UpdateFileActivity.this);
        recyclerView.setAdapter(imageAdapter);

        imageAdapter.setClickCall(new ImageAdapter.ClickCall() {
            @Override
            public void click(String url) {
                i=i-urls.size();
                getImg();
            }
        });
        gson = new Gson();
        upArchivesPicPresenter = new UpArchivesPicPresenter(new UpArchivesPic());
        updateArchivesPresenter = new UpdateArchivesPresenter(new UpdateArchives());
        imageAdapter.setCloseCall(new ImageAdapter.CloseCall() {
            @Override
            public void click(int i) {
                urls.remove(i);
            }
        });
    }

    @Override
    protected void destoryData() {

    }
    Map<String,String> map=new HashMap<>();
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.yes) {
            map.clear();
            String bingshi = UpdateFileActivity.this.bingshi.getText().toString();
            String bingzheng = UpdateFileActivity.this.bingzheng.getText().toString();
            String jBingshi = UpdateFileActivity.this.jBingshi.getText().toString();
            String jingli = UpdateFileActivity.this.jingli.getText().toString();
            String guocheng = UpdateFileActivity.this.guocheng.getText().toString();
            String start_time = UpdateFileActivity.this.start_time.getText().toString();
            String end_time = UpdateFileActivity.this.end_time.getText().toString();
            map.put("diseaseMain",bingzheng);
            map.put("diseaseNow",bingshi);
            map.put("diseaseBefore",jBingshi);
            map.put("treatmentHospitalRecent",jingli);
            map.put("treatmentProcess",guocheng);
            map.put("treatmentStartTime",start_time);
            map.put("treatmentEndTime",end_time);
            String s = gson.toJson(map);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);

        } else {
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
    public class UpdateArchives implements DataCall<Result>{
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
                .mutiSelectMaxSize(6-urls.size())
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
        ImageSelector.open(UpdateFileActivity.this, imageConfig);
    }
    private List<String> urls=new ArrayList<>();
    public class UserArchives implements DataCall<Result<UserArchivesBean>> {

        @Override
        public void success(Result<UserArchivesBean> data, Object... args) {
            urls.clear();
            imageAdapter.clear();
            UserArchivesBean result = data.getResult();
            if (null != result) {
                bingzheng.setText(result.diseaseMain);
                bingshi.setText(result.diseaseNow);
                jBingshi.setText(result.diseaseBefore);
                jingli.setText(result.treatmentHospitalRecent);
                guocheng.setText(result.treatmentProcess);
                long treatmentStartTime = result.treatmentStartTime;
                long treatmentEndTime = result.treatmentEndTime;
                String start = DateUtils.timeStamp2Date(treatmentStartTime, "yyyy.MM.dd");
                String end = DateUtils.timeStamp2Date(treatmentEndTime, "yyyy.MM.dd");

                start_time.setText(start);
                end_time.setText(end);
                String[] split = result.picture.split(",");
                urls.add(path+"/add.png");
                if (split.length>0){
                    for (int i = 0; i < split.length; i++) {
                        urls.add(split[i]);
                    }
                }
                imageAdapter.setList(urls);
                imageAdapter.notifyDataSetChanged();
            }
        }


        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    private String realPathFromUri;
    private List<File> files=new ArrayList<>();
    private List<String> paths2=new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 200:

                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    paths.clear();
                    paths2.addAll(pathList);
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
            Toast.makeText(UpdateFileActivity.this, "data为空 ！", Toast.LENGTH_LONG).show();
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
}
