package com.bw.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.PlateBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bw.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/12 09:25
 */
public class PlateListAdapter extends RecyclerView.Adapter<PlateListAdapter.Holder> {
    private Context context;
    private List<PlateBean> list=new ArrayList<>();
    private boolean aa=true;
    public PlateListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.platelist_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.one.setText(list.get(position).name);
        if (position==0){
            list.get(position).isChecked=aa;
        }
        holder.one.setChecked(list.get(position).isChecked);
        holder.one.setTag(list.get(position));
        holder.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 PlateBean tag = (PlateBean) view.getTag();
               /* RadioButton radioButton = (RadioButton) view;
                tag.isChecked=radioButton.isChecked();*/
                //实现单选，第一种方法，十分简单， Lv Rv通用,因为它们都有notifyDataSetChanged()方法
                // 每次点击时，先将所有的selected设为false，并且将当前点击的item 设为true， 刷新整个视图
                if (position>0){
                    aa=false;
                }else {
                    aa=true;
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isChecked=false;
                }

                tag.isChecked=true;
                notifyDataSetChanged();
                call.call(tag);
            }
        });
    }


    public PlateBean getId(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked){
                return list.get(i);
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<PlateBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        RadioButton one;
        public Holder(@NonNull View itemView) {
            super(itemView);
            one=itemView.findViewById(R.id.one);
        }
    }
    private  Call call;

    public void setCall(Call call) {
        this.call = call;
    }

    public interface Call{
        void call(PlateBean plateBean);
    }
}
