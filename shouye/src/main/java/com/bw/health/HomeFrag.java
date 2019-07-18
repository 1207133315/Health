package com.bw.health;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.adapter.DepartmentAdapter;
import com.bw.health.adapter.MationListAdapter;

import com.bw.health.adapter.PlateListAdapter;
import com.bw.health.bean.BannerBean;
import com.bw.health.bean.DepartmentBean;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.PlateBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDFragment;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.DepartmentPresenter;
import com.bw.health.presenter.MationListPresenter;
import com.bw.health.presenter.PlateListPresenter;
import com.bw.health.presenter.ShowBannerPresenter;
import com.bw.health.view.MyImageView;
import com.bw.health.view.ViewPagerStop;

import java.util.ArrayList;
import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    @BindView(R2.id.bookView)
    ViewPager vp;
    @BindView(R2.id.yaopin)
    LinearLayout yaopin;
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


    }
    public  class Banner implements DataCall<Result<List<BannerBean>>> {
        @Override
        public void success(Result<List<BannerBean>> data, Object... args) {


        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(getContext(), ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
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


}
