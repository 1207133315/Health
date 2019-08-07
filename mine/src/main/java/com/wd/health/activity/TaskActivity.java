package com.wd.health.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.R;
import com.wd.health.R2;
import com.wd.health.adapter.StringAdapter;
import com.wd.health.bean.FindUserTaskListBean;
import com.wd.health.presenter.DoTaskPresenter;
import com.wd.health.presenter.FindUserSignPresenter;
import com.wd.health.presenter.FindUserTaskListPresenter;
import com.wd.health.presenter.ReceiveRewardPresenter;
import com.wd.health.presenter.WhetherSignTodayPresenter;
import com.wd.health.view.Myprogressbar;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = "/TaskActivity/")
public class TaskActivity extends WDActivity {


    @BindView(R2.id.back)
    ImageView back;
    @BindView(R2.id.recy1)
    RecyclerView recy1;
    @BindView(R2.id.progress)
    Myprogressbar progress;
    @BindView(R2.id.recy2)
    RecyclerView recy2;
    @BindView(R2.id.qiandao)
    TextView qiandao;
    @BindView(R2.id.shoufabingyouquan)
    TextView shoufabingyouquan;
    @BindView(R2.id.bingyouquanshouping)
    TextView bingyouquanshouping;
    @BindView(R2.id.wanshanxinxi)
    TextView wanshanxinxi;
    @BindView(R2.id.jiankangceping)
    TextView jiankangceping;
    private StringAdapter stringAdapter;
    private WhetherSignTodayPresenter whetherSignTodayPresenter;
    private FindUserSignPresenter findUserSignPresenter;
    private DoTaskPresenter doTaskPresenter;
    private FindUserTaskListPresenter findUserTaskListPresenter;
    private Long id;
    private String sessionId;
    private ReceiveRewardPresenter receiveRewardPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        ArrayList<String> list = new ArrayList<>();
        list.add("+1");
        list.add("+2");
        list.add("+3");
        list.add("+4");
        list.add("+5");
        list.add("+10");
        list.add("+10");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1天");
        list2.add("2天");
        list2.add("3天");
        list2.add("4天");
        list2.add("5天");
        list2.add("6天");
        list2.add("N天");
        recy1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stringAdapter = new StringAdapter(R.layout.strings, list);
        recy1.setAdapter(stringAdapter);
        recy2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stringAdapter = new StringAdapter(R.layout.strings, list2);
        recy2.setAdapter(stringAdapter);
        List<LoginBean> list1 = GetDaoUtil.getGetDaoUtil().getUserDao().queryBuilder().list();
        id = list1.get(0).getId();
        sessionId = list1.get(0).getSessionId();

        findUserSignPresenter = new FindUserSignPresenter(new FindUserSign());


        findUserTaskListPresenter = new FindUserTaskListPresenter(new FindUserTaskList());
        receiveRewardPresenter = new ReceiveRewardPresenter(new ReceiveReward());
    }

    @Override
    protected void onResume() {
        super.onResume();
        findUserTaskListPresenter.reqeust(id.intValue(), sessionId);
        findUserSignPresenter.reqeust(id.intValue(), sessionId);
    }

    @Override
    protected void destoryData() {

    }




    @OnClick({R2.id.qiandao, R2.id.shoufabingyouquan, R2.id.bingyouquanshouping, R2.id.wanshanxinxi, R2.id.jiankangceping,R2.id.back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.qiandao) {
            if (qiandao.getText().equals("去完成")){
                intentByRouter("/MineActivity/");
            }else if (qiandao.getText().equals("领H币")){
                receiveRewardPresenter.reqeust(id.intValue(),sessionId,1001);
            }
        } else if (i == R.id.shoufabingyouquan) {
            if (shoufabingyouquan.getText().equals("去完成")){
                intentByRouter("/CircleWritActivity/");
            }else if (shoufabingyouquan.getText().equals("领H币")){
                receiveRewardPresenter.reqeust(id.intValue(),sessionId,1003);
            }
        } else if (i == R.id.bingyouquanshouping) {
            if (bingyouquanshouping.getText().equals("去完成")){
                ARouter.getInstance().build("/HomeActivity/")
                        .withBoolean("istask", true)
                        .navigation();

            }else if (bingyouquanshouping.getText().equals("领H币")){
                receiveRewardPresenter.reqeust(id.intValue(),sessionId,1002);
            }
        } else if (i == R.id.wanshanxinxi) {
            if (wanshanxinxi.getText().equals("去完成")){
                intentByRouter("/MineMessageActivity/");
            }else if (wanshanxinxi.getText().equals("领H币")){
                receiveRewardPresenter.reqeust(id.intValue(),sessionId,1004);
            }
        } else if (i == R.id.jiankangceping) {
            if (jiankangceping.getText().equals("去完成")){
                intentByRouter("/CepingActivity/");
            }else if (jiankangceping.getText().equals("领H币")){
                receiveRewardPresenter.reqeust(id.intValue(),sessionId,1005);
            }
        } else if (i == R.id.back) {
            finish();
        }
    }
    //查询签到天数
    public class FindUserSign implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result result= (Result) data;
            if ((double)result.getResult()>6.0){
                progress.setindex(6);
            }else {
                double a=(double)result.getResult();
                int b= (int) a;
                progress.setindex(b);
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    //查询用户任务列表
    public class FindUserTaskList implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            Result<List<FindUserTaskListBean>> result= (Result<List<FindUserTaskListBean>>) data;
            List<FindUserTaskListBean> list = result.getResult();

            for (FindUserTaskListBean bean : list) {
                Log.d("FindUserTaskList", "bean.getId():" + bean.getId());
                if (bean.getId()==1001){
                    if (bean.getWhetherFinish()==1){
                        if (bean.getWhetherReceive()==1){
                            qiandao.setBackgroundResource(R.drawable.btnshape3);
                            qiandao.setTextColor(Color.parseColor("#ffffff"));
                            qiandao.setText("领H币");
                        }else if (bean.getWhetherReceive()==2){
                            qiandao.setBackgroundResource(R.drawable.btnshape);
                            qiandao.setTextColor(Color.parseColor("#333333"));
                            qiandao.setText("已完成");
                        }else if (bean.getWhetherReceive()==3){
                            qiandao.setBackgroundResource(R.drawable.btnshape2);
                            qiandao.setTextColor(Color.parseColor("#ffffff"));
                            qiandao.setText("未完成");
                        }
                    }else {
                        qiandao.setBackgroundResource(R.drawable.btnshape2);
                        qiandao.setTextColor(Color.parseColor("#ffffff"));
                        qiandao.setText("去完成");
                    }
                }else if (bean.getId()==1002){
                    if (bean.getWhetherFinish()==1){
                        if (bean.getWhetherReceive()==1){
                            bingyouquanshouping.setBackgroundResource(R.drawable.btnshape3);
                            bingyouquanshouping.setTextColor(Color.parseColor("#ffffff"));
                            bingyouquanshouping.setText("领H币");
                        }else if (bean.getWhetherReceive()==2){
                            bingyouquanshouping.setBackgroundResource(R.drawable.btnshape);
                            bingyouquanshouping.setTextColor(Color.parseColor("#333333"));
                            bingyouquanshouping.setText("已完成");
                        }else if (bean.getWhetherReceive()==3){
                            bingyouquanshouping.setBackgroundResource(R.drawable.btnshape2);
                            bingyouquanshouping.setTextColor(Color.parseColor("#ffffff"));
                            bingyouquanshouping.setText("未完成");
                        }
                    }else {
                        bingyouquanshouping.setBackgroundResource(R.drawable.btnshape2);
                        bingyouquanshouping.setTextColor(Color.parseColor("#ffffff"));
                        bingyouquanshouping.setText("去完成");
                    }

                }else if (bean.getId()==1003){
                    if (bean.getWhetherFinish()==1){
                        if (bean.getWhetherReceive()==1){
                            shoufabingyouquan.setBackgroundResource(R.drawable.btnshape3);
                            shoufabingyouquan.setTextColor(Color.parseColor("#ffffff"));
                            shoufabingyouquan.setText("领H币");
                        }else if (bean.getWhetherReceive()==2){
                            shoufabingyouquan.setBackgroundResource(R.drawable.btnshape);
                            shoufabingyouquan.setTextColor(Color.parseColor("#333333"));
                            shoufabingyouquan.setText("已完成");
                        }else if (bean.getWhetherReceive()==3){
                            shoufabingyouquan.setBackgroundResource(R.drawable.btnshape2);
                            shoufabingyouquan.setTextColor(Color.parseColor("#ffffff"));
                            shoufabingyouquan.setText("未完成");
                        }
                    }else {
                        shoufabingyouquan.setBackgroundResource(R.drawable.btnshape2);
                        shoufabingyouquan.setTextColor(Color.parseColor("#ffffff"));
                        shoufabingyouquan.setText("去完成");
                    }
                }else if (bean.getId()==1004){
                    if (bean.getWhetherFinish()==1){
                        if (bean.getWhetherReceive()==1){
                            wanshanxinxi.setBackgroundResource(R.drawable.btnshape3);
                            wanshanxinxi.setTextColor(Color.parseColor("#ffffff"));
                            wanshanxinxi.setText("领H币");
                        }else if (bean.getWhetherReceive()==2){
                            wanshanxinxi.setBackgroundResource(R.drawable.btnshape);
                            wanshanxinxi.setTextColor(Color.parseColor("#333333"));
                            wanshanxinxi.setText("已完成");
                        }else if (bean.getWhetherReceive()==3){
                            wanshanxinxi.setBackgroundResource(R.drawable.btnshape2);
                            wanshanxinxi.setTextColor(Color.parseColor("#ffffff"));
                            wanshanxinxi.setText("未完成");
                        }
                    }else {
                        wanshanxinxi.setBackgroundResource(R.drawable.btnshape2);
                        wanshanxinxi.setTextColor(Color.parseColor("#ffffff"));
                        wanshanxinxi.setText("去完成");
                    }
                }else if (bean.getId()==1005){
                    if (bean.getWhetherFinish()==1){
                        if (bean.getWhetherReceive()==1){
                            jiankangceping.setBackgroundResource(R.drawable.btnshape3);
                            jiankangceping.setTextColor(Color.parseColor("#ffffff"));
                            jiankangceping.setText("领H币");
                        }else if (bean.getWhetherReceive()==2){
                            jiankangceping.setBackgroundResource(R.drawable.btnshape);
                            jiankangceping.setTextColor(Color.parseColor("#333333"));
                            jiankangceping.setText("已完成");
                        }else if (bean.getWhetherReceive()==3){
                            jiankangceping.setBackgroundResource(R.drawable.btnshape2);
                            jiankangceping.setTextColor(Color.parseColor("#ffffff"));
                            jiankangceping.setText("未完成");
                        }
                    }else {
                        jiankangceping.setBackgroundResource(R.drawable.btnshape2);
                        jiankangceping.setTextColor(Color.parseColor("#ffffff"));
                        jiankangceping.setText("去完成");
                    }
                }else if (bean.getId()==1006){

                }else if (bean.getId()==1007){

                }
            }
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    //领取任务奖励
    public class ReceiveReward implements DataCall{

        @Override
        public void success(Object data, Object... args) {
            findUserTaskListPresenter.reqeust(id.intValue(), sessionId);
            
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(TaskActivity.this, data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
