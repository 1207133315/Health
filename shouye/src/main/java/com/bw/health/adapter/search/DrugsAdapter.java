package com.bw.health.adapter.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.activity.YPDetailActivity;
import com.bw.health.bean.SearchBean;

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
 * @date 2019 2019/07/22 19:24
 */
public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.ViewHolder> {
    private Context context;
    private List<SearchBean.DrugsItme> list=new ArrayList<>();

    public DrugsAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<SearchBean.DrugsItme> list) {
        if (list!=null){
            this.list.addAll(list);
        }
    }
    public void clear() {
        list.clear();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.messige_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size()>0){
            holder.name.setText(list.get(position).drugsName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(context, YPDetailActivity.class);
                    intent.putExtra("id",list.get(position).drugsId);
                    intent.putExtra("name",list.get(position).drugsName);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R2.id.name)
            TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
