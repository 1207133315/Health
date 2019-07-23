package com.bw.health;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.health.activity.SearchActivity;
import com.bw.health.adapter.BannerAdapter;
import com.bw.health.adapter.DepartmentAdapter;
import com.bw.health.adapter.MationListAdapter;

import com.bw.health.adapter.PlateListAdapter;
import com.bw.health.bean.BannerBean;
import com.bw.health.bean.DepartmentBean;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.PlateBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.DepartmentPresenter;
import com.bw.health.presenter.MationListPresenter;
import com.bw.health.presenter.PlateListPresenter;
import com.bw.health.presenter.ShowBannerPresenter;
import com.bw.health.util.GetDaoUtil;
import com.bw.health.view.MyImageView;
import com.bw.health.view.MyTextView;
import com.bw.health.view.NoslideRecyclerview;
import com.bw.health.view.ViewPagerStop;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * com.bw.health
 *
 * @author 李宁康
 * @date 2019 2019/07/11 14:16
 */
public class HomeFrag extends WDFragment {

    /* @BindView(R2.id.bookView)
     BookPageView bookPageView;*/
    @BindView(R2.id.next)
    TextView next;
    @BindView(R2.id.plateRecycler)
    RecyclerView plateRecycler;
    @BindView(R2.id.mationRecycler)
    RecyclerView mationRecycler;
    @BindView(R2.id.unplateRecycler)
    RecyclerView unplateRecycler;
    @BindView(R2.id.cotent)
    LinearLayout cotent;
    @BindView(R2.id.keshiRecycler)
    RecyclerView keshiRecycler;

    @BindView(R2.id.bingzheng)
    LinearLayout bingzheng;
    @BindView(R2.id.banner)
    NoslideRecyclerview banner;
    @BindView(R2.id.yaopin)
    LinearLayout yaopin;
    @BindView(R2.id.head)
    ImageView head;
    private ShowBannerPresenter showBannerPresenter;
    private PlateListPresenter plateListPresenter;
    private PlateListAdapter plateListAdapter;
    private MationListPresenter mationListPresenter;
    private MationListAdapter mationListAdapter;
    @BindView(R2.id.scrollView)
    NestedScrollView scrollView;
    private DepartmentPresenter departmentPresenter;
    private DepartmentAdapter departmentAdapter;
    private List<MyImageView> list=new ArrayList<>();
    private BannerAdapter bannerAdapter;
    @BindView(R2.id.yuandian)
    RadioGroup yuandian;
    private List<BannerBean> result;
    private List<RadioButton> yuanList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int position;
    @BindView(R2.id.search)
    MyTextView search;
    @Override
    public String getPageName() {
        return "首页";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_frag_layout;
    }

    @SuppressLint({"ResourceAsColor", "InvalidR2Usage", "WrongConstant"})
    @Override
    protected void initView() {
        showBannerPresenter = new ShowBannerPresenter(new Banner());
        plateListPresenter = new PlateListPresenter(new PlateList());
        plateRecycler.setLayoutManager(new GridLayoutManager(getContext(),5));
        unplateRecycler.setLayoutManager(new GridLayoutManager(getContext(),5));
        plateListAdapter = new PlateListAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        banner.setLayoutManager(linearLayoutManager);
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(banner);
        bannerAdapter = new BannerAdapter(getContext());

        showBannerPresenter.reqeust();

       plateListPresenter.reqeust();

       //首页多条目
        mationListPresenter = new MationListPresenter(new MationList());
        mationRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mationListPresenter.reqeust((long)1,1,5);
        mationListAdapter = new MationListAdapter(getContext());
        mationRecycler.setAdapter(mationListAdapter);
        //单击切换
        plateListAdapter.setCall(new PlateListAdapter.Call() {
            @Override
            public void call(PlateBean plateBean) {
                mationListPresenter.reqeust((long)plateBean.id,1,5);
            }
        });

        //科室列表
        departmentPresenter = new DepartmentPresenter(new Department());
        keshiRecycler.setLayoutManager(new GridLayoutManager(getContext(),4));
        departmentPresenter.reqeust();
        departmentAdapter = new DepartmentAdapter(getContext());
        keshiRecycler.setAdapter(departmentAdapter);
        //吸顶
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i1>cotent.getHeight()-30){
                    unplateRecycler.setVisibility(View.VISIBLE);
                    plateRecycler.setVisibility(View.INVISIBLE);

                }else {
                    unplateRecycler.setVisibility(View.GONE);
                    plateRecycler.setVisibility(View.VISIBLE);

                }
            }
        });


       banner.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               if (newState == RecyclerView.SCROLL_STATE_IDLE){
                   for (int i = 0; i < yuanList.size(); i++) {
                        int position = linearLayoutManager.findFirstVisibleItemPosition();
                        position = position %yuanList.size();
                       if (i== position){
                            yuanList.get(i).setChecked(true);
                       }else {
                           yuanList.get(i).setChecked(false);
                       }
                   }

               }
           }

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
           }
       });
        handler.sendEmptyMessageDelayed(0,2000);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            position = linearLayoutManager.findFirstVisibleItemPosition();
            banner.smoothScrollToPosition(position+1);
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };

    public  class Banner implements DataCall<Result<List<BannerBean>>> {


        @Override
        public void success(Result<List<BannerBean>> data, Object... args) {
            result = data.getResult();
            bannerAdapter.setList(data.getResult());
            banner.setAdapter(bannerAdapter);
            yuanList.clear();
            for (int i = 0; i <result.size() ; i++) {
                 RadioButton radioButton = new RadioButton(getContext());
                Bitmap a=null;
                radioButton.setButtonDrawable(new BitmapDrawable(a));
                radioButton.setBackgroundResource(R.drawable.yuandian_select);
                yuanList.add(radioButton);




                //这是布局参数,,刚开始小圆点之间没有距离,所以使用java代码指定宽度高度,并且指定小圆点之间的距离
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(30,30);
                 params.topMargin=30;

                //放到LinearLayout,也就是圆点布局中
                yuandian.addView(radioButton,params);

            }
            yuanList.get(0).setChecked(true);
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
         LoginBeanDao userDao = GetDaoUtil.getGetDaoUtil().getUserDao();
         List<LoginBean> loginBeans = userDao.loadAll();
         if (loginBeans.size()>0){
             Glide.with(getContext()).load(loginBeans.get(0).getHeadPic())
                     .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                     .into(head);
         }

    }

    @OnClick({R2.id.bingzheng,R2.id.yaopin,R2.id.head})
    public void yaopinOrbingzheng(View view){
        Bundle bundle = new Bundle();
        final int id = view.getId();
        if (id==R.id.bingzheng){
            bundle.putInt("i",1);
            intentByRouter("/YpOrBzActivity/",bundle);
        }else if (id==R.id.yaopin){
            bundle.putInt("i",2);
            intentByRouter("/YpOrBzActivity/",bundle);
        }else if (id==R.id.head){
            intentByRouter("/MineActivity/");
        }
    }

    @OnClick(R2.id.next)
    public void nextClick(){
         Bundle bundle = new Bundle();
        final PlateBean plateBean = plateListAdapter.getId();
        bundle.putLong("id", plateBean.id);
        bundle.putString("title",plateBean.name);
        intentByRouter("/ZiXunActivity/",bundle);
    }

    public class Department implements DataCall<Result<List<DepartmentBean>>>{
        @Override
        public void success(Result<List<DepartmentBean>> data, Object... args) {
            departmentAdapter.clear();
            departmentAdapter.addList(data.getResult());
            departmentAdapter.notifyDataSetChanged();
            departmentAdapter.re(new DepartmentAdapter.aa() {
                @Override
                public void po(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", (int) data.getResult().get(position).id);
                    List<LoginBean> list = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
                    if (list.size()>0){
                        intentByRouter("/DoctorlistActivity/",bundle);
                    }else {
                        intentByRouter("/LoginActivity/");
                    }

                }
            });
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage()+data.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public class MationList implements DataCall<Result<List<MationBean>>>{
        @Override
        public void success(Result<List<MationBean>> data, Object... args) {
            final List<MationBean> result = data.getResult();
            for (int i = 0; i < result.size(); i++) {
                    String[] split = result.get(i).thumbnail.split(";");
                    if (split.length == 1) {
                        result.get(i).aa = 1;
                    } else if (split.length >= 3) {
                        result.get(i).aa=2;
                    }else {

                        result.get(i).aa = 3;
                    }


            }
            mationListAdapter.clear();
            mationListAdapter.addList(result);
            mationListAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    public  class PlateList implements DataCall<Result<List<PlateBean>>>{
            @Override
            public void success(Result<List<PlateBean>> data, Object... args) {

                plateListAdapter.addList(data.getResult());
                unplateRecycler.setAdapter(plateListAdapter);
                plateRecycler.setAdapter(plateListAdapter);

            }

            @Override
            public void fail(ApiException data, Object... args) {

            }
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
