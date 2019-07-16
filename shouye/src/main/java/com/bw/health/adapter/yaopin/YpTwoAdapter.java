package com.bw.health.adapter.yaopin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.bean.YaoPinTwoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.bw.health.adapter.yaopin
 *
 * @author 李宁康
 * @date 2019 2019/07/15 11:06
 */
public class YpTwoAdapter extends RecyclerView.Adapter<YpTwoAdapter.ViewHolder> {
    private Context context;
    private List<YaoPinTwoBean> list=new ArrayList<>();

    public YpTwoAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<YaoPinTwoBean> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    @NonNull
    @Override
    public YpTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.yaopin_two_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull YpTwoAdapter.ViewHolder holder, int position) {
        holder.img.setImageURI(list.get(position).picture);
         String name = list.get(position).name;
        if (name.contains("]")) {
            for (int i = 0; i < name.length(); i++) {
                final String s = String.valueOf(name.charAt(i));
                if (s.equals("]")) {
                    final String substring = name.substring(0, i + 1);
                    holder.name1.setText(substring);
                    holder.name2.setText(name.substring(i + 1, name.length()));
                }
            }
        }else {

            holder.name1.setText(name.substring(0,3));
            holder.name2.setText(name.substring(3,name.length()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.img)
        SimpleDraweeView img;
        @BindView(R2.id.name1)
        TextView name1;
        @BindView(R2.id.name2)
        TextView name2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
