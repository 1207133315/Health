package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.health.circle.R;
import com.wd.health.bean.CircleUserInfoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleUserInfoAdapter extends RecyclerView.Adapter<CircleUserInfoAdapter.MyViewHolder> {
    Context context;
    List<CircleUserInfoBean> list;
    private MyViewHolder myViewHolder;

    public CircleUserInfoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public CircleUserInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circleuserinfo_item_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleUserInfoAdapter.MyViewHolder holder, int position) {
        CircleUserInfoBean circleUserInfoBean = list.get(position);

        String title = circleUserInfoBean.getTitle();
        int amount = circleUserInfoBean.getAmount();
        long releaseTime = circleUserInfoBean.getReleaseTime();
        String detail = circleUserInfoBean.getDetail();
        int collectionNum = circleUserInfoBean.getCollectionNum();
        int commentNum = circleUserInfoBean.getCommentNum();


        myViewHolder.tv_title.setText(title);
        myViewHolder.tv_hbi_num.setText(amount + "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        String time = simpleDateFormat.format(new Date(releaseTime));
        myViewHolder.tv_time.setText(time + "");
        myViewHolder.tv_detail.setText(detail);
        myViewHolder.tv_shoucang_num.setText(collectionNum + "");
        myViewHolder.tv_jianyi_num.setText(commentNum + "");

        //增加的悬赏额度,等于0则没有额外悬赏
        if (amount == 0) {
            myViewHolder.tv_image_hbi.setVisibility(View.GONE);
            myViewHolder.tv_hbi_num.setVisibility(View.GONE);
        } else {
            myViewHolder.tv_image_hbi.setVisibility(View.VISIBLE);
            myViewHolder.tv_hbi_num.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<CircleUserInfoBean> userinfo_result) {
        list.addAll(userinfo_result);
    }

    public void clear() {
        list.clear();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_title;
        ImageView tv_image_hbi;
        TextView tv_hbi_num;
        TextView tv_time;
        TextView tv_detail;
        TextView tv_shoucang_num;
        TextView tv_jianyi_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.circle_list_title);
            tv_image_hbi = itemView.findViewById(R.id.circle_list_hbi);
            tv_hbi_num = itemView.findViewById(R.id.circle_list_hbi_num);

            tv_time = itemView.findViewById(R.id.circle_list_time);
            tv_detail = itemView.findViewById(R.id.circle_list_detail);
            tv_shoucang_num = itemView.findViewById(R.id.circle_list_shoucang_num);
            tv_jianyi_num = itemView.findViewById(R.id.circle_list_jianyi_num);


        }
    }


}
