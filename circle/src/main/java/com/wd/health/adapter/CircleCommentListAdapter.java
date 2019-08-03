package com.wd.health.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.health.bean.CircleListBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;
import com.bw.health.exception.ApiException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;
import com.wd.health.activity.CircleUserInfoActivity;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.presenter.ExpressOpinionPresenter;

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
    private String sessionId;
    private Long id_user;
    private int opinion;

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
        int commentUserId = commentlistBean.getCommentUserId();
        int commentId = commentlistBean.getCommentId();
        //赞同、反对
        opinion = commentlistBean.getOpinion();


        myViewHolder.simp_image.setImageURI(headPic);
        myViewHolder.tv_name.setText(nickName);
        myViewHolder.tv_comment.setText(content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String time = simpleDateFormat.format(new Date(commentTime));
        myViewHolder.tv_time.setText(time + "");

        myViewHolder.tv_goods1_num.setText(supportNum + "");
        myViewHolder.tv_goods2_num.setText(opposeNum + "");


        if (whetherDoctor == 1) {
            myViewHolder.biaoshi_image.setVisibility(View.VISIBLE);
            myViewHolder.renzheng_image.setVisibility(View.VISIBLE);

        } else {
            myViewHolder.biaoshi_image.setVisibility(View.GONE);
            myViewHolder.renzheng_image.setVisibility(View.GONE);

        }
        //判断是否为支持
        if (supportNum == 1) {
            myViewHolder.tv_goods1.setBackgroundResource(R.drawable.common_icon_agree_s);
        } else {
            myViewHolder.tv_goods1.setBackgroundResource(R.drawable.common_icon_agree_n);

        }
        //判断是否反对
        if (opposeNum == 1) {
            myViewHolder.tv_goods2.setBackgroundResource(R.drawable.common_icon_disagree_s);
        } else {
            myViewHolder.tv_goods2.setBackgroundResource(R.drawable.common_icon_disagree_n);

        }


        //---------- 发表支持的观点-----------------------
        //数据库
        LoginBeanDao loginBeanDao = DaoMaster.newDevSession(context, LoginBeanDao.TABLENAME).getLoginBeanDao();
        sessionId = loginBeanDao.loadAll().get(0).getSessionId();
        id_user = loginBeanDao.loadAll().get(0).getId();

       /* Log.i("login", sessionId);
        Log.i("login", id_user + "");*/

        if (opinion == 1) {
            myViewHolder.tv_goods1.setChecked(true);
            myViewHolder.tv_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentlistBean.setOpinion(2);
                    ExpressOpinionPresenter expressOpinionPresenter = new ExpressOpinionPresenter(new ExpressOpinionCall());
                    expressOpinionPresenter.reqeust(String.valueOf(id_user), sessionId, commentId + "", "2");
                    notifyDataSetChanged();
                }
            });
        } else {
            myViewHolder.tv_goods1.setChecked(false);
            myViewHolder.tv_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentlistBean.setOpinion(1);
                    ExpressOpinionPresenter expressOpinionPresenter = new ExpressOpinionPresenter(new ExpressOpinionCall());
                    expressOpinionPresenter.reqeust(String.valueOf(id_user), sessionId, commentId + "", "1");
                    notifyDataSetChanged();
                }
            });

        }





        //---------- 发表支持的观点-------尾巴-----------------

        //---------- 发表反对的观点----------------------------




        //---------- 发表反对的观点---------尾巴---------------

        //点击头像跳转病友的朋友圈
        myViewHolder.simp_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CircleUserInfoActivity.class);
                intent.putExtra("commentUserId", String.valueOf(commentUserId));
                intent.putExtra("headPic", headPic);
                intent.putExtra("nickName", nickName);
                context.startActivity(intent);
                call.showCall();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void getData(List<CircleCommentListBean> comment_result) {
        mList.addAll(comment_result);
    }

    //清除缓存
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

    public Call call;

    public void setCall(Call call) {
        this.call = call;
    }

    public interface Call {
        void showCall();
    }

    public Data datas;

    public void setDatas(Data datas) {
        this.datas = datas;
    }

    public interface Data {
        void showData();
    }


    //---------- 发表支持的观点----成功---失败-------------------

    class ExpressOpinionCall implements DataCall<Result> {

        @Override
        public void success(Result data, Object... args) {
            Object result = data.getResult();

            Toast.makeText(context, "赞同", Toast.LENGTH_SHORT).show();

            //  Toast.makeText(context, "反对", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void fail(ApiException data, Object... args) {

            String displayMessage = data.getDisplayMessage();
            if (displayMessage.equals("请先登陆")) {
                datas.showData();
            }
        }
    }


    //---------- 发表支持的观点-------尾巴-----------------


}
