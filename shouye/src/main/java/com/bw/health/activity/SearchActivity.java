package com.bw.health.activity;


import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.adapter.RemenAdapter;
import com.bw.health.adapter.search.DiseaseAdapter;
import com.bw.health.adapter.search.DoctorAdapter;
import com.bw.health.adapter.search.DrugsAdapter;
import com.bw.health.bean.RemenBean;
import com.bw.health.bean.Result;
import com.bw.health.bean.SearchBean;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.RemenBeanDao;
import com.bw.health.exception.ApiException;
import com.bw.health.presenter.HomeSearchPresenter;
import com.bw.health.presenter.RemenSearchPresenter;
import com.bw.health.view.MyView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends WDActivity {

    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.context)
    EditText context;
    @BindView(R2.id.search)
    TextView search;
    @BindView(R2.id.top)
    RelativeLayout top;
    @BindView(R2.id.lishi)
    RecyclerView lishi;
    @BindView(R2.id.remen)
    MyView remen;
    @BindView(R2.id.result)
    RecyclerView result;
    @BindView(R2.id.yisheng)
    RecyclerView yisheng;
    @BindView(R2.id.yaopin)
    RecyclerView yaopin;
    @BindView(R2.id.bingzheng)
    RecyclerView bingzheng;
    @BindView(R2.id.aa)
    TextView aa;
    private RemenSearchPresenter remenSearchPresenter;
    private RemenBeanDao remenBeanDao;
    private RemenAdapter remenAdapter;
    @BindView(R2.id.one)
    LinearLayout one;
    @BindView(R2.id.messige)
    LinearLayout messige;
    @BindView(R2.id.yisheng_name)
    TextView yisheng_name;
    @BindView(R2.id.bingzheng_name)
    TextView bingzheng_name;
    @BindView(R2.id.yaopin_name)
    TextView yaopin_name;
    @BindView(R2.id.Null)
    LinearLayout Null;
    @BindView(R2.id.Null_data)
    TextView Null_data;

    private HomeSearchPresenter homeSearchPresenter;

    private DoctorAdapter doctorAdapter;
    private DiseaseAdapter diseaseAdapter;
    private DrugsAdapter drugsAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }
    private boolean isRelease;
    private List<RemenBean> remenList=new ArrayList<>();
    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        one.setVisibility(View.VISIBLE);
        messige.setVisibility(View.GONE);
        result.setVisibility(View.GONE);
        remenSearchPresenter = new RemenSearchPresenter(new Remen());
        remenSearchPresenter.reqeust();
        homeSearchPresenter = new HomeSearchPresenter(new HomeSearch());
        yisheng.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        yaopin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bingzheng.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        doctorAdapter = new DoctorAdapter(this);
        diseaseAdapter = new DiseaseAdapter(this);
        drugsAdapter = new DrugsAdapter(this);
        yisheng.setAdapter(doctorAdapter);
        yaopin.setAdapter(drugsAdapter);
        bingzheng.setAdapter(diseaseAdapter);
        remenBeanDao = DaoMaster.newDevSession(SearchActivity.this, RemenBeanDao.TABLENAME).getRemenBeanDao();
        lishi.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  s = context.getText().toString();

                if (s.length()>0&&!s.equals("")){
                    homeSearchPresenter.reqeust(s);

                    addLishi(s);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
         RemenBeanDao remenBeanDao = DaoMaster.newDevSession(SearchActivity.this, RemenBeanDao.TABLENAME).getRemenBeanDao();
        if (remenBeanDao.loadAll().size()>0){
            lishi.setVisibility(View.VISIBLE);
            aa.setVisibility(View.VISIBLE);
        }else {
            aa.setVisibility(View.GONE);
            lishi.setVisibility(View.GONE);
        }
        remenAdapter = new RemenAdapter(this);
        lishi.setAdapter(remenAdapter);
        remenAdapter.notifyDataSetChanged();

        remenAdapter.setRemenClick(new RemenAdapter.RemenClick() {
            @Override
            public void click(RemenBean remenBean) {
                context.setText(remenBean.name);
                homeSearchPresenter.reqeust(remenBean.name);
                context.setText(remenBean.name);
            }
        });

    }

    //加入搜索历史
    public void addLishi( String s){
        if (s.length()>0&&!s.equals("")){
            RemenBean remenBean = new RemenBean();
            remenBean.name=s;
            remenList.add(remenBean);
            remenBeanDao = DaoMaster.newDevSession(SearchActivity.this, RemenBeanDao.TABLENAME).getRemenBeanDao();
            List<RemenBean> remenBeans = remenBeanDao.loadAll();
            if (remenBeans.size()>0) {
                for (int i = 0; i < remenBeans.size(); i++) {
                    if (!remenBeans.get(i).name.equals(remenBean.name)) {
                        isRelease = true;
                    } else {
                        isRelease = false;
                    }
                }
                if (isRelease)
                    remenBeanDao.insertOrReplaceInTx(remenBean);
            }else {
                remenBeanDao.insertOrReplaceInTx(remenBean);
            }

        }
    }

    public class HomeSearch implements DataCall<Result<SearchBean>>{
        @Override
        public void success(Result<SearchBean> data, Object... args) {
             SearchBean result1 = data.getResult();
             doctorAdapter.clear();
             drugsAdapter.clear();
             diseaseAdapter.clear();
             if (result1.doctorSearchVoList.size()>0&&result1.doctorSearchVoList!=null){
                 doctorAdapter.setList(result1.doctorSearchVoList);
                 yisheng_name.setVisibility(View.VISIBLE);
                 yisheng.setVisibility(View.VISIBLE);
                 doctorAdapter.notifyDataSetChanged();
             }else {
                 yisheng_name.setVisibility(View.GONE);
                 yisheng.setVisibility(View.GONE);
             }
            if (result1.diseaseSearchVoList.size()>0&&result1.diseaseSearchVoList!=null){
                diseaseAdapter.setList(result1.diseaseSearchVoList);
                bingzheng_name.setVisibility(View.VISIBLE);
                bingzheng.setVisibility(View.VISIBLE);
                diseaseAdapter.notifyDataSetChanged();
            }else {
                bingzheng_name.setVisibility(View.GONE);
                bingzheng.setVisibility(View.GONE);
            }
            if (result1.drugsSearchVoList.size()>0&&result1.drugsSearchVoList!=null){
                drugsAdapter.setList(result1.drugsSearchVoList);
                yaopin_name.setVisibility(View.VISIBLE);
                yaopin.setVisibility(View.VISIBLE);
                drugsAdapter.notifyDataSetChanged();
            }else {
                yaopin_name.setVisibility(View.GONE);
                yaopin.setVisibility(View.GONE);
            }

            if (result1.drugsSearchVoList.size()<=0
                    &&result1.doctorSearchVoList.size()<=0
                    &&result1.diseaseSearchVoList.size()<=0){
                Null.setVisibility(View.VISIBLE);
                Null_data.setText("抱歉!没有找到"+"'"+context.getText().toString()+"'相关的病友圈");
                Null_data.setVisibility(View.VISIBLE);
                messige.setVisibility(View.GONE);
            }else {
                Null.setVisibility(View.GONE);
                messige.setVisibility(View.VISIBLE);
            }


            one.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    protected void destoryData() {

    }

    private List<TextView> list=new ArrayList<>();
   public class Remen implements DataCall<Result<List<RemenBean>>>{
       @Override
       public void success(Result<List<RemenBean>> data, Object... args) {
           final List<RemenBean> result = data.getResult();
           for (int i = 0; i < data.getResult().size(); i++) {
                TextView textView = new TextView(SearchActivity.this);
                textView.setText(result.get(i).name);
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.remen_bg);
                list.add(textView);
                textView.setPadding(30,30,30,30);
               //放到LinearLayout,也就是圆点布局中
               remen.addView(textView);
           }
           for (int i = 0; i < remen.getChildCount(); i++) {
               remen.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                        TextView textView = (TextView) view;
                       homeSearchPresenter.reqeust(textView.getText().toString());
                       context.setText(textView.getText().toString());
                       addLishi(textView.getText().toString());
                   }
               });
           }
       }

       @Override
       public void fail(ApiException data, Object... args) {

       }
   }

    @OnClick({R2.id.back, R2.id.context, R2.id.search, R2.id.top, R2.id.result})
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            if (Null.getVisibility()==View.VISIBLE){
                one.setVisibility(View.VISIBLE);
                Null.setVisibility(View.GONE);
            }else

            if (messige.getVisibility()==View.VISIBLE){
                one.setVisibility(View.VISIBLE);
                messige.setVisibility(View.GONE);
            }else

            if (one.getVisibility()==View.VISIBLE){
                finish();
            }
        } else if (i == R.id.context) {
        } else if (i == R.id.search) {
        } else if (i == R.id.top) {
        } else if (i == R.id.result) {
        } else {
        }
    }
}
