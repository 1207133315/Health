package com.wd.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.MySickCircleCommentListBean;
import com.wd.health.bean.OutsideBean;
import com.wd.health.presenter.AdoptionProposalPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleElseCommentAdapter extends RecyclerView.Adapter<CircleElseCommentAdapter.MyViewHolder> {
    private Context context;
    private List<MySickCircleCommentListBean.OtherSickCircleCommentListBean> mList = new ArrayList<>();

    public CircleElseCommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CircleElseCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circle_else_comment_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleElseCommentAdapter.MyViewHolder holder, int position) {
        MySickCircleCommentListBean.OtherSickCircleCommentListBean otherSicklist = mList.get(position);

        String headPic = otherSicklist.getHeadPic();
        String nickName = otherSicklist.getNickName();
        String content = otherSicklist.getContent();
        long commentTime = otherSicklist.getCommentTime();
        int whetherDoctor = otherSicklist.getWhetherDoctor();
        int supportNum = otherSicklist.getSupportNum();
        int opposeNum = otherSicklist.getOpposeNum();
        int commentUserId = otherSicklist.getCommentUserId();
        int commentId = otherSicklist.getCommentId();
        //赞同、反对
        int opinion = otherSicklist.getOpinion();


        holder.simp_image.setImageURI(headPic);
        holder.tv_name.setText(nickName);
        holder.tv_comment.setText(content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String time = simpleDateFormat.format(new Date(commentTime));
        holder.tv_time.setText(time + "");

        holder.tv_goods1_num.setText(supportNum + "");
        holder.tv_goods2_num.setText(opposeNum + "");


        //判断是否是医生
        if (whetherDoctor == 1) {
            holder.biaoshi_image.setVisibility(View.VISIBLE);
            holder.renzheng_image.setVisibility(View.VISIBLE);

        } else {
            holder.biaoshi_image.setVisibility(View.GONE);
            holder.renzheng_image.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setDatt(List<MySickCircleCommentListBean.OtherSickCircleCommentListBean> otherBean) {
        mList.addAll(otherBean);
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
            simp_image = itemView.findViewById(R.id.else_comment_item_doctor_image);
            renzheng_image = itemView.findViewById(R.id.else_comment_item_doctor_renzheng);
            tv_name = itemView.findViewById(R.id.else_comment_item_name);
            biaoshi_image = itemView.findViewById(R.id.else_comment_item_doctor_biaoshi);
            tv_comment = itemView.findViewById(R.id.else_comment_item_comment);
            tv_time = itemView.findViewById(R.id.else_comment_item_time);

            tv_goods1 = itemView.findViewById(R.id.else_comment_item_goods1);
            tv_goods1_num = itemView.findViewById(R.id.else_comment_item_goods1_num);
            tv_goods2 = itemView.findViewById(R.id.else_comment_item_goods2);
            tv_goods2_num = itemView.findViewById(R.id.else_comment_item_goods2_num);


        }
    }
}
