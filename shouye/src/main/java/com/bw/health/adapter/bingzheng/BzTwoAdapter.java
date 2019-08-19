package com.bw.health.adapter.bingzheng;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.shouye.R;
import com.bw.health.shouye.R2;
import com.bw.health.activity.BZDetailActivity;
import com.bw.health.bean.BingZhengBean;

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
 * @date 2019 2019/07/14 19:43
 */
public class BzTwoAdapter extends RecyclerView.Adapter<BzTwoAdapter.ViewHolder> {
    private Context context;
    private List<BingZhengBean> list=new ArrayList<>();

    public BzTwoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bingzheng_two_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(list.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(context, BZDetailActivity.class);
                    intent.putExtra("id",list.get(position).id);
                    intent.putExtra("name",list.get(position).name);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<BingZhengBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public void clecar() {
        list.clear();
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
