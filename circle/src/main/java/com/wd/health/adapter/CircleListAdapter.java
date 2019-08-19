package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.CircleFindDepartmentBean;
import com.bw.health.bean.CircleListBean;
import com.wd.health.circle.R;
import com.wd.health.bean.WaiBuBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.MyViewHolder> {
    Context context;
    List<CircleListBean> mList;
    private MyViewHolder myViewHolder;

    public CircleListAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();

    }



    @NonNull
    @Override
    public CircleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circlelist_item_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleListAdapter.MyViewHolder holder, int position) {
        CircleListBean listBean = mList.get(position);
        String title = listBean.getTitle();
        int amount = listBean.getAmount();
        long releaseTime = listBean.getReleaseTime();
        String detail = listBean.getDetail();
        int collectionNum = listBean.getCollectionNum();
        int commentNum = listBean.getCommentNum();
        int sickCircleId = listBean.getSickCircleId();

        myViewHolder.tv_title.setText(title);
        myViewHolder.tv_hbi_num1.setText(String.valueOf(amount));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        String time = simpleDateFormat.format(new Date(releaseTime));
        myViewHolder.tv_time.setText(time + "");
        myViewHolder.tv_detail.setText(detail);
        myViewHolder.tv_shoucang_num.setText(String.valueOf(collectionNum));
        myViewHolder.tv_jianyi_num.setText(String.valueOf(commentNum));


        //增加的悬赏额度,等于0则没有额外悬赏
        if (amount==0){
            holder.tv_image_hbi1.setVisibility(View.GONE);
            holder.tv_hbi_num1.setVisibility(View.GONE);
        }else {
            holder.tv_image_hbi1.setBackgroundResource(R.mipmap.h_currency);
        }

       myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dataCall.showCall(mList.get(position));
               WaiBuBean.setSickCircleId_w(sickCircleId);
               //Toast.makeText(context, ""+sickCircleId, Toast.LENGTH_SHORT).show();

           }
       });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void getDatt(List<CircleListBean> result) {
        if (!result.isEmpty()) {
            mList.clear();
        }
        mList.addAll(result);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_title;
        ImageView tv_image_hbi1;
        TextView tv_hbi_num1;
        TextView tv_time;
        TextView tv_detail;
        TextView tv_shoucang_num;
        TextView tv_jianyi_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.circle_list_title);
            tv_image_hbi1 = itemView.findViewById(R.id.circle_list_hbi1);
            tv_hbi_num1 = itemView.findViewById(R.id.circle_list_hbi_num);

            tv_time = itemView.findViewById(R.id.circle_list_time);
            tv_detail = itemView.findViewById(R.id.circle_list_detail);
            tv_shoucang_num = itemView.findViewById(R.id.circle_list_shoucang_num);
            tv_jianyi_num = itemView.findViewById(R.id.circle_list_jianyi_num);


        }
    }

    public  interface Call {
        void showCall(CircleListBean circleListBean);
    }

    public static Call dataCall;

    public static void setCall(Call call) {
        dataCall = call;
    }
}
