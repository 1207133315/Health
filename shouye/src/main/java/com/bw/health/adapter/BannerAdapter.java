package com.bw.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.health.R;
import com.bw.health.R2;
import com.bw.health.activity.BannerDetailActivity;
import com.bw.health.bean.BannerBean;
import com.facebook.drawee.view.SimpleDraweeView;

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
 * @date 2019 2019/07/21 18:12
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context context;
    private List<BannerBean> list=new ArrayList<>();
    private int index;
    public BannerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_banner_itme,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size()>0)
        index=position%list.size();
        if (list.get(index).imageUrl!=null&&list.get(index).imageUrl.length()>0)
        holder.img.setImageURI(Uri.parse(list.get(index).imageUrl));
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(context, BannerDetailActivity.class);
                intent.putExtra("url",list.get(index).jumpUrl);
                context.startActivity(intent);
            }
        });
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void setList(List<BannerBean> result) {
        if (!result.isEmpty()){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.img)
        SimpleDraweeView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
