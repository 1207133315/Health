package com.bw.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.health.shouye.R;
import com.bw.health.activity.MationDetailActivity;

import com.bw.health.adapter.holder.HolderOne;
import com.bw.health.adapter.holder.HolderThree;
import com.bw.health.adapter.holder.HolderTwo;
import com.bw.health.bean.MationBean;
import com.bw.health.adapter.holder.BaseHolder;

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

        if (viewType == 1) {
            return new HolderOne(LayoutInflater.from(context).inflate(R.layout.home_itme_one, parent, false));
        }
        if (viewType == 2) {
            return new HolderTwo(LayoutInflater.from(context).inflate(R.layout.home_itme_two, parent, false));
        }
        if (viewType == 3) {
            return new HolderThree(LayoutInflater.from(context).inflate(R.layout.home_itme_three, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         BaseHolder holder1 = (BaseHolder) holder;
        holder1.onBindView(context,list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(context, MationDetailActivity.class);
                    intent.putExtra("id",list.get(position).id);
                context.startActivity(intent);
            }
        });
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
