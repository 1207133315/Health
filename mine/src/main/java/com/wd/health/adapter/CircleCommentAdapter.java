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
import com.wd.health.activity.PatientsCircleActivity;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.OutsideBean;
import com.wd.health.bean.PatientsCircleBean;
import com.wd.health.presenter.AdoptionProposalPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleCommentAdapter extends RecyclerView.Adapter<CircleCommentAdapter.MyViewHolder> {
    private Context context;
    private List<CircleCommentListBean> mList = new ArrayList<>();
    private Long id_user;
    private String sessionId;

    public CircleCommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CircleCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.circle_comment_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircleCommentAdapter.MyViewHolder holder, int position) {
        CircleCommentListBean circleCommentListBean = mList.get(position);
        String headPic = circleCommentListBean.getHeadPic();
        String nickName = circleCommentListBean.getNickName();
        String content = circleCommentListBean.getContent();
        long commentTime = circleCommentListBean.getCommentTime();
        int whetherDoctor = circleCommentListBean.getWhetherDoctor();
        int supportNum = circleCommentListBean.getSupportNum();
        int opposeNum = circleCommentListBean.getOpposeNum();
        int commentUserId = circleCommentListBean.getCommentUserId();
        int commentId = circleCommentListBean.getCommentId();
        //赞同、反对
        int opinion = circleCommentListBean.getOpinion();


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

        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(context, LoginBeanDao.TABLENAME).getLoginBeanDao();
        id_user = loginBeanDao.loadAll().get(0).getId();
        sessionId = loginBeanDao.loadAll().get(0).getSessionId();

        //采纳病友圈优秀的评论
        holder.tv_adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_outside = OutsideBean.getId_outside();
                AdoptionProposalPresenter adoptionProposalPresenter = new AdoptionProposalPresenter(new AdoptionCall());
                adoptionProposalPresenter.reqeust(String.valueOf(id_user), sessionId, String.valueOf(commentId), id_outside);
                //  Toast.makeText(context, "病友圈id:" + id_outside, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<CircleCommentListBean> result_comment) {
        mList.addAll(result_comment);
    }

    public void clear() {
        mList.clear();
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
        private final CheckBox tv_adoption;
        private final TextView tv_adoption_num;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            simp_image = itemView.findViewById(R.id.comment_item_doctor_image);
            renzheng_image = itemView.findViewById(R.id.comment_item_doctor_renzheng);
            tv_name = itemView.findViewById(R.id.comment_item_name);
            biaoshi_image = itemView.findViewById(R.id.comment_item_doctor_biaoshi);
            tv_comment = itemView.findViewById(R.id.comment_item_comment);
            tv_time = itemView.findViewById(R.id.comment_item_time);

            tv_goods1 = itemView.findViewById(R.id.comment_item_goods1);
            tv_goods1_num = itemView.findViewById(R.id.comment_item_goods1_num);
            tv_goods2 = itemView.findViewById(R.id.comment_item_goods2);
            tv_goods2_num = itemView.findViewById(R.id.comment_item_goods2_num);
            tv_adoption = itemView.findViewById(R.id.comment_item_caina);
            tv_adoption_num = itemView.findViewById(R.id.comment_item_caina_num);


        }
    }

    //采纳病友圈优秀的评论
    class AdoptionCall implements DataCall<Result> {

        @Override
        public void success(Result data, Object... args) {
            Toast.makeText(context, "采纳成功！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
}
