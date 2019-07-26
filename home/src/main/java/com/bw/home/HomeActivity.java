package com.bw.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.bw.health.HomeFrag;
import com.bw.health.bean.CircleListBean;
import com.bw.health.bean.LoginBean;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.util.GetDaoUtil;
import com.kd.easybarrage.BarrageView;
import com.wd.health.ShiPinFragment;
import com.wd.health.adapter.CircleListAdapter;
import com.wd.health.bean.VideoBean;
import com.wd.health.frag.CircleFrag;
import com.wd.health.frag.FindSickCircleInfoFrag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/HomeActivity/")
public class HomeActivity extends WDActivity {


    @BindView(R2.id.frame)
    FrameLayout frame;
    @BindView(R2.id.home)
    RadioButton home;
    @BindView(R2.id.video)
    RadioButton video;
    @BindView(R2.id.circle)
    RadioButton circle;
    @BindView(R2.id.rg)
    RadioGroup rg;
    @BindView(R2.id.top)
    RelativeLayout top;
    private InputMethodManager imm;
    private FragmentTransaction transaction;
    private HomeFrag homeFrag;
    private CircleFrag circleFrag;
    private FindSickCircleInfoFrag findSickCircleInfoFrag;
    private ShiPinFragment shiPinFragment;

    public boolean flag = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    Fragment currentFragment;

    @Override
    protected void initView() {
        homeFrag = new HomeFrag();
        //病友圈
        currentFragment = homeFrag;
        circleFrag = new CircleFrag();
        shiPinFragment = new ShiPinFragment();
        findSickCircleInfoFrag = new FindSickCircleInfoFrag();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, homeFrag).show(homeFrag).commit();
        home.setChecked(true);
        CircleListAdapter.setCall(new CircleListAdapter.Call() {
            @Override
            public void showCall(CircleListBean circleListBean) {
                showFragment(findSickCircleInfoFrag);
                EventBus.getDefault().postSticky(circleListBean);
            }
        });
        EventBus.getDefault().register(this);
       /* shiPinFragment.setiSkill(new ShiPinFragment.ISkill() {
            @Override
            public void eat(boolean isShow) {
                if (isShow){
                    circle.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.VISIBLE);
                }else {
                    circle.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                }
            }
        });*/


    }


    @Override
    protected void destoryData() {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                circle.setChecked(false);
                circle.setVisibility(View.GONE);
                rg.setVisibility(View.GONE);
                top.setVisibility(View.GONE);
                needVisible = false;
            }
        }
    };


    @SuppressLint("InvalidR2Usage")
    @OnClick({R2.id.home, R2.id.video, R2.id.circle})
    public void onClick(View v) {
        handler.removeMessages(1);//不管点哪个按钮都可以移除,哪怕页面根本就没再过视频页
        if (v.getId() == R.id.home) {
            handler.removeMessages(1);
            circle.setChecked(false);
            showFragment(homeFrag);
            top.setVisibility(View.GONE);
            needVisible = false;
        } else if (v.getId() == R.id.video) {
            if (needVisible) {
                handler.sendEmptyMessageDelayed(1, 5000);
            } else {
                showFragment(shiPinFragment);
                circle.setChecked(false);
                circle.setVisibility(View.GONE);
                rg.setVisibility(View.GONE);
                top.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.circle) {
            if (flag) {
                intentByRouter("/CircleWritActivity/");
                flag=false;
            } else {
                handler.removeMessages(1);
                showFragment(circleFrag);
                home.setChecked(false);
                video.setChecked(false);
                top.setVisibility(View.GONE);
                needVisible = false;
                flag=true;
            }
        }

    }

    boolean needVisible;//是否触发了五秒隐藏,handleMessage方法里面一定要改成false

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void fullScreenVisible(Boolean visible) {
        if (visible) {
            rg.setVisibility(View.VISIBLE);
            circle.setVisibility(View.VISIBLE);
            //把标题也写在这里
            top.setVisibility(View.VISIBLE);
            needVisible = true;
            handler.sendEmptyMessageDelayed(1, 5000);
        }
        EventBus.getDefault().removeAllStickyEvents();
    }


    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.frame, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }
}
