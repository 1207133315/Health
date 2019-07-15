package com.bw.health.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.adapter.YinDaoPagerAdapter;
import com.bw.health.bean.LoginBean;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.DaoSession;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.fragment.Yindao1;
import com.bw.login.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

@Route(path = "MainActivity")
public class MainActivity extends WDActivity {

    private ViewPager firingpager;
    private RadioButton btn_1;
    private RadioButton btn_2;
    private RadioButton btn_3;
    private RadioButton btn_4;
    private RadioButton btn_5;
    private RadioGroup yindaoGroup;
    int currentItem;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SharedPreferences first = getSharedPreferences("first", Context.MODE_PRIVATE);
            boolean a = first.getBoolean("a", true);

            if (msg.what == 0) {
                if (a) {
                    first.edit().putBoolean("a", false).commit();
                    firingpager.setVisibility(View.VISIBLE);
                    yindaoGroup.setVisibility(View.VISIBLE);
                    Yindao1 yindao1 = new Yindao1();
                    yindao1.setview1(R.mipmap.guide_pages_one, "专业的在线问诊");
                    Yindao1 yindao2 = new Yindao1();
                    yindao2.setview1(R.mipmap.guide_pages_two, "丰富的健康常识");
                    Yindao1 yindao3 = new Yindao1();
                    yindao3.setview1(R.mipmap.guide_pages_three, "专业的在线问诊");
                    Yindao1 yindao4 = new Yindao1();
                    yindao4.setview1(R.mipmap.guide_pages_four, "丰富的健康常识");
                    Yindao1 yindao5 = new Yindao1();
                    yindao5.setview1(R.mipmap.guide_pages_five, "专业的在线问诊");
                    List<Fragment> list = new ArrayList<Fragment>();
                    list.add(yindao1);
                    list.add(yindao2);
                    list.add(yindao3);
                    list.add(yindao4);
                    list.add(yindao5);
                    final YinDaoPagerAdapter yinDaoPagerAdapter = new YinDaoPagerAdapter(getSupportFragmentManager(), list);
                    firingpager.setAdapter(yinDaoPagerAdapter);

                    firingpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int i, float v, int i1) {

                        }

                        @Override
                        public void onPageSelected(int i) {
                            RadioButton childAt = (RadioButton) yindaoGroup.getChildAt(i);
                            childAt.setChecked(true);
                            if(i==4){
                                msjr.setVisibility(View.VISIBLE);
                            }else {
                                msjr.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int i) {

                        }
                    });
                } else {
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MainActivity.this, "login");
                    DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
                    DaoSession daoSession = daoMaster.newSession();
                    LoginBeanDao loginBeanDao = daoSession.getLoginBeanDao();
                    List<LoginBean> list = loginBeanDao.queryBuilder().list();

                    if (list.size()>0&&list!=null){
                        intentByRouter("/HomeActivity/");
                    }else {

                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        finish();
                    }
                }

            } else {
                Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }


        }
    };
    private Button msjr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        firingpager = findViewById(R.id.firingpager);
        yindaoGroup = findViewById(R.id.yindao_group);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        msjr = findViewById(R.id.msjr);
        //判断网络连接状态
        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            //有网的时候
            handler.sendEmptyMessageDelayed(0, 1000);
        } else {
            //没网的时候
            handler.sendEmptyMessageDelayed(1, 1000);
        }
        msjr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void destoryData() {

    }
}
