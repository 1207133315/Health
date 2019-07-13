package com.bw.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bw.health.R;
import com.bw.health.adapter.holder.BaseHolder;
import com.bw.health.adapter.holder.HolderOne;
import com.bw.health.adapter.holder.HolderThree;
import com.bw.health.adapter.holder.HolderTwo;
import com.bw.health.bean.MationBean;

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

    public MationListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final int itemViewType = getItemViewType(viewType);
        switch (itemViewType){
            case 0:
                return new HolderOne(LayoutInflater.from(context).inflate(R.layout.home_itme_one,parent,false));
            case 1:
              return   new HolderTwo(LayoutInflater.from(context).inflate(R.layout.home_itme_two,parent,false));
            case 2:
              return   new HolderThree(LayoutInflater.from(context).inflate(R.layout.home_itme_three,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.onBindView(list,context,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

            if (list.get(position).aa==1){
                return 0;
            }else if (list.get(position).aa==2){
                return 1;
             }else {
                return 2;
            }


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
