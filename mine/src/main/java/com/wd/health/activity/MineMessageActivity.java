package com.wd.health.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.ModifyHeadPicPresenter;
import com.wd.health.utils.getPhotoFromPhotoAlbum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Route(path = "/MineMessageActivity/")
public class MineMessageActivity extends WDActivity {

    @BindView(R2.id.head)
    SimpleDraweeView head;
    @BindView(R2.id.name)
    TextView name;
    @BindView(R2.id.touxiang)
    RelativeLayout touxiang;
    @BindView(R2.id.nc)
    RelativeLayout nc;
    @BindView(R2.id.xingbie)
    RelativeLayout xingbie;
    @BindView(R2.id.tz)
    RelativeLayout tz;
    @BindView(R2.id.youxiang)
    RelativeLayout youxiang;
    @BindView(R2.id.bdwx)
    RelativeLayout bdwx;
    @BindView(R2.id.shimingrenzheng)
    RelativeLayout shimingrenzheng;
    @BindView(R2.id.bdyhk)
    RelativeLayout bdyhk;
    @BindView(R2.id.sex)
    ImageView sex;
    @BindView(R2.id.shengao)
    TextView shengao;
    @BindView(R2.id.tizhong)
    TextView tizhong;
    @BindView(R2.id.nianling)
    TextView nianling;
    @BindView(R2.id.email)
    TextView email;
    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.vxbd)
    TextView vxbd;
    @BindView(R2.id.rzbd)
    TextView rzbd;
    @BindView(R2.id.yhkbd)
    TextView yhkbd;
    private List<LoginBean> list;
    // 拍照的照片的存储位置
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    private Uri imageUri;
    private ModifyHeadPicPresenter modifyHeadPicPresenter;
    private PopupWindow popupWindow;
    private DoTaskPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_message;
    }

    @Override
    protected void initView() {
        modifyHeadPicPresenter = new ModifyHeadPicPresenter(new updateheadpic());
        presenter = new DoTaskPresenter(new DoTask());

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
        list = loginBeanDao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            name.setText(list.get(0).getNickName());
            head.setImageURI(list.get(0).getHeadPic());
            int sex1 = list.get(0).getSex();
            if (sex1 == 1) {
                sex.setImageResource(R.mipmap.common_icon_boy_n);
            } else {
                sex.setImageResource(R.mipmap.common_icon_girl_n);
            }
            int height = list.get(0).getHeight();
            int weight = list.get(0).getWeight();
            int age = list.get(0).getAge();

            shengao.setText(height + "");
            tizhong.setText(weight + "");
            nianling.setText(age + "");
            if (list.get(0).getEmail() != "" && list.get(0).getEmail() != null) {
                email.setText(list.get(0).getEmail());
            }
            int whetherBingWeChat = list.get(0).getWhetherBingWeChat();
            if (whetherBingWeChat == 1) {
                vxbd.setText("已绑定");
            }
        } else {
            intentByRouter("/LoginActivity/");
        }
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R2.id.touxiang, R2.id.nc, R2.id.xingbie, R2.id.youxiang, R2.id.bdwx, R2.id.shimingrenzheng, R2.id.bdyhk, R2.id.back, R2.id.tz})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.touxiang) {
            View view1 = View.inflate(this, R.layout.popwindow, null);
            popupWindow = new PopupWindow(view1,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            popupWindow.showAtLocation(MineMessageActivity.this.findViewById(R.id.aaa),
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

        } else if (i == R.id.nc) {
            Intent intent = new Intent(this, UpdateNicknameActivity.class);
            intent.putExtra("name", list.get(0).getNickName());
            startActivity(intent);
        } else if (i == R.id.xingbie) {
            Intent intent = new Intent(this, UpdateSexActivity.class);
            startActivity(intent);
        } else if (i == R.id.youxiang) {
        } else if (i == R.id.bdwx) {
        } else if (i == R.id.shimingrenzheng) {
        } else if (i == R.id.bdyhk) {
        } else if (i == R.id.back) {
            finish();
        } else if (i == R.id.tz) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bm = BitmapFactory.decodeFile(mTempPhotoPath, options);
            File file1 = getFile(bm);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            MultipartBody.Part MultipartFile =
                    MultipartBody.Part.createFormData("image", file1.getName(), requestFile);

            modifyHeadPicPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), MultipartFile);
            JMessageClient.updateUserAvatar(file1, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Toast.makeText(MineMessageActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MineMessageActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (requestCode == 200) {
            String realPathFromUri = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            if (realPathFromUri != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bm = BitmapFactory.decodeFile(realPathFromUri, options);
                File file1 = getFile(bm);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                MultipartBody.Part MultipartFile =
                        MultipartBody.Part.createFormData("image", file1.getName(), requestFile);
                modifyHeadPicPresenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), MultipartFile);
                JMessageClient.updateUserAvatar(file1, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            Toast.makeText(MineMessageActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MineMessageActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class updateheadpic implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result result = (Result) data;

            if (result.getMessage().equals("请先登陆")) {
                Toast.makeText(MineMessageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                intentByRouter("/LoginActivity/");
            } else {
                LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
                List<LoginBean> list = userDao.queryBuilder().list();
                for (LoginBean loginBean : list) {
                    loginBean.setHeadPic((String) result.getResult());
                    userDao.insertOrReplace(loginBean);
                    head.setImageURI(loginBean.getHeadPic());
                }
                presenter.reqeust(list.get(0).getId().intValue(), list.get(0).getSessionId(), 1004);
            }
            popupWindow.dismiss();
        }

        @Override
        public void fail(ApiException data, Object... args) {

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

    public class DoTask implements DataCall {

        @Override
        public void success(Object data, Object... args) {
            Result result = (Result) data;
            Toast.makeText(MineMessageActivity.this, "result.getResult():" + result.getResult(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
