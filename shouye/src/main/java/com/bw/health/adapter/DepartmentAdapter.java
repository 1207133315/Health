package com.bw.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.activity.YpOrBzActivity;
import com.bw.health.bean.DepartmentBean;
import com.bw.health.bean.InedxOrId;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

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
 * @date 2019 2019/07/14 16:05
 */
public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    private Context context;
    private List<DepartmentBean> list=new ArrayList<>();

    public DepartmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.department_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageURI(Uri.parse(list.get(position).pic));
        holder.name.setText(list.get(position).departmentName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(context, YpOrBzActivity.class);
                final Bundle bundle = new Bundle();
                bundle.putInt("i",1);
                intent.putExtras(bundle);
                final InedxOrId inedxOrId = new InedxOrId(position, list.get(position).id);
                EventBus.getDefault().postSticky(inedxOrId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<DepartmentBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.img)
        SimpleDraweeView img;
        @BindView(R2.id.name)
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
