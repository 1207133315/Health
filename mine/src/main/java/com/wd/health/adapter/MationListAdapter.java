package com.wd.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bw.health.bean.LoginBean;
import com.bw.health.bean.MationBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.wd.health.mine.R;
import com.wd.health.adapter.holder.BaseHolder;
import com.wd.health.adapter.holder.HolderOne;
import com.wd.health.adapter.holder.HolderThree;
import com.wd.health.adapter.holder.HolderTwo;
import com.wd.health.presenter.UnZiXunPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.bw.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/12 11:17
 */
public class MationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MationBean> list=new ArrayList<>();
    private  LoginBean loginBean;

    public MationListAdapter(Context context) {
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size()>0)
        loginBean = loginBeans.get(0);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            return new HolderOne(LayoutInflater.from(context).inflate(R.layout.home_itme_one1, parent, false));
        }
        if (viewType == 2) {
            return new HolderTwo(LayoutInflater.from(context).inflate(R.layout.home_itme_two2, parent, false));
        }
        if (viewType == 3) {
            return new HolderThree(LayoutInflater.from(context).inflate(R.layout.home_itme_three3, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         int itemViewType = getItemViewType(position);

        if (itemViewType==1){
             HolderOne holder1 = (HolderOne) holder;
            holder1.onBindView(context,list.get(position));
            holder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final UnZiXunPresenter unZiXunPresenter = new UnZiXunPresenter(new UnShoucang());
                    unZiXunPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),list.get(position).infoId);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        if (itemViewType==2){
            final HolderTwo holder1 = (HolderTwo) holder;
           holder1 .onBindView(context,list.get(position));
            holder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final UnZiXunPresenter unZiXunPresenter = new UnZiXunPresenter(new UnShoucang());
                    unZiXunPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),list.get(position).infoId);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

        }
        if (itemViewType==3){
            final HolderThree holder1 = (HolderThree) holder;
            holder1.onBindView(context,list.get(position));
            holder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final UnZiXunPresenter unZiXunPresenter = new UnZiXunPresenter(new UnShoucang());
                    unZiXunPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),list.get(position).infoId);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
       /* final BaseHolder holder1 = (BaseHolder) holder;
        holder1.onBindView(context,list.get(position));
        holder1.itemView.setOnTouchListener(new View.OnTouchListener() {

            private float moveX;
            private float x;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (x-moveX>=80){
                            holder1.delete.setVisibility(View.VISIBLE);
                        }else {
                            holder1.delete.setVisibility(View.GONE);
                        }
                        moveX=0;
                        break;
                }

                return true;
            }
        });*/

    }

    public class UnShoucang implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(context, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {
            Toast.makeText(context, ""+data.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

            if (list.get(position).aa==1){
                return 1;
            }else if (list.get(position).aa==2){
                return 2;
             }else if (list.get(position).aa==3){
                return 3;
            }
            return 3;


    }

    public void addList(List<MationBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }
}
