package com.bw.health.adapter.bingzheng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.DepartmentBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bw.health.adapter.bingzheng
 *
 * @author 李宁康
 * @date 2019 2019/07/14 17:55
 */
public class BzOneAdapter extends RecyclerView.Adapter<BzOneAdapter.ViewHolder> {
    private Context context;
    private List<DepartmentBean> list=new ArrayList<>();
    private boolean aa=true;
    public BzOneAdapter(Context context) {
        this.context = context;
    }
    private int index=0;

    public void setIndex(int index) {
        this.index = index;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bingzheng_one_itme,parent,false));
    }

    @SuppressLint({"ResourceAsColor", "InvalidR2Usage"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DepartmentBean departmentBean = list.get(position);
        holder.name.setText(departmentBean.departmentName);
        if (index==position){
            departmentBean.isChecked=aa;
        }
        if (position==0&&index==0){
            departmentBean.isChecked=aa;
        }
        if (departmentBean.isChecked){
            holder.linear.setBackgroundColor(Color.WHITE);
            holder.view.setVisibility(View.VISIBLE);
        }else {
            holder.linear.setBackgroundResource(R.drawable.keshi_bg);
            holder.view.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setTag(departmentBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 DepartmentBean tag = (DepartmentBean) view.getTag();
                if (position!=index){
                    aa=false;
                }else {
                    aa=true;
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isChecked=false;
                }
                list.get(position).isChecked=true;
                notifyDataSetChanged();
                call.dataCall(tag);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<DepartmentBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       @BindView(R2.id.linear)
        LinearLayout linear;
       @BindView(R2.id.view)
       View view;
       @BindView(R2.id.name)
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private Call call;

    public void setCall(Call call) {
        this.call = call;
    }

    public interface Call{
        void dataCall(DepartmentBean departmentBean);
    }
}
