package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.health.bean.CircleListBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.bean.CircleCommentListBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleCommentListAdapter extends RecyclerView.Adapter<CircleCommentListAdapter.MyViewHolder> {
    Context context;
    List<CircleCommentListBean> mList;
    private MyViewHolder myViewHolder;

    public CircleCommentListAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }


    @NonNull
    @Override
    public CircleCommentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circlecommentlist_item_layout, null);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleCommentListAdapter.MyViewHolder holder, int position) {
        CircleCommentListBean commentlistBean = mList.get(position);
        String headPic = commentlistBean.getHeadPic();
        String nickName = commentlistBean.getNickName();
        String content = commentlistBean.getContent();
        long commentTime = commentlistBean.getCommentTime();
        int whetherDoctor = commentlistBean.getWhetherDoctor();
        int supportNum = commentlistBean.getSupportNum();
        int opposeNum = commentlistBean.getOpposeNum();



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView simp_image;
        private final ImageView renzheng_image;
        private final TextView tv_name;
        private final ImageView biaoshi_image;
        private final TextView tv_comment;
        private final TextView tv_time;
        private final CheckBox tv_goods1;
        private final TextView tv_goods1_num;
        private final CheckBox tv_goods2;
        private final TextView tv_goods2_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            simp_image = itemView.findViewById(R.id.circle_pop_doctor_image);
            renzheng_image = itemView.findViewById(R.id.circle_pop_doctor_renzheng);
            tv_name = itemView.findViewById(R.id.circle_pop_name);
            biaoshi_image = itemView.findViewById(R.id.circle_pop_doctor_biaoshi);
            tv_comment = itemView.findViewById(R.id.circle_pop_comment);
            tv_time = itemView.findViewById(R.id.circle_pop_time);

            tv_goods1 = itemView.findViewById(R.id.circle_pop_goods1);
            tv_goods1_num = itemView.findViewById(R.id.circle_pop_goods1_num);
            tv_goods2 = itemView.findViewById(R.id.circle_pop_goods2);
            tv_goods2_num = itemView.findViewById(R.id.circle_pop_goods2_num);


        }
    }

}
