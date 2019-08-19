package com.wd.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wd.health.video.R;
import com.wd.health.video.R2;
import com.wd.health.bean.LeimuBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.wd.health.adapter
 *
 * @author 李宁康
 * @date 2019 2019/07/17 11:41
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private Context context;
    private List<LeimuBean> list=new ArrayList<>();
    private boolean aa=true;
    public CategoryListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.top_itme,parent,false));
    }

    public void setlist(List<LeimuBean> leimuBeans) {
        if (leimuBeans!=null){
            list.addAll(leimuBeans);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(list.get(position).name);
        if (position==0){
            list.get(position).isChecked=aa;
        }
        holder.name.setChecked(list.get(position).isChecked);
        holder.name.setTag(list.get(position));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeimuBean tag = (LeimuBean) view.getTag();
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
                call.dataCall(tag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.name)
        RadioButton name;
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
        void dataCall(LeimuBean leimuBean);
    }
}
