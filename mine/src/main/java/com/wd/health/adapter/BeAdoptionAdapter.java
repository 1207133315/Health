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
import com.wd.health.bean.BeAdoptedBean;
import com.wd.health.bean.CircleCommentListBean;
import com.wd.health.bean.OutsideBean;
import com.wd.health.presenter.AdoptionProposalPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BeAdoptionAdapter extends RecyclerView.Adapter<BeAdoptionAdapter.MyViewHolder> {
    private Context context;
    private List<BeAdoptedBean> mList = new ArrayList<>();


    public BeAdoptionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BeAdoptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.be_adopted_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeAdoptionAdapter.MyViewHolder holder, int position) {
        BeAdoptedBean beAdoptedBean = mList.get(position);
        String releaseUserHeadPic = beAdoptedBean.getReleaseUserHeadPic();
        String releaseUserNickName = beAdoptedBean.getReleaseUserNickName();
        String title = beAdoptedBean.getTitle();
        String disease = beAdoptedBean.getDisease();
        long adoptTime = beAdoptedBean.getAdoptTime();
        String content = beAdoptedBean.getContent();


        holder.simp_image.setImageURI(releaseUserHeadPic);
        holder.tv_name.setText(releaseUserNickName);
        holder.tv_title.setText(title);
        holder.tv_disease.setText("主要病症：" + disease);
        holder.tv_comment.setText(content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String time = simpleDateFormat.format(new Date(adoptTime));
        holder.tv_time.setText("采纳时间：" + time + "");


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<BeAdoptedBean> result_be) {
        mList.addAll(result_be);
    }

    public void clear() {
        mList.clear();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView simp_image;
        private final TextView tv_name;
        private final TextView tv_comment;
        private final TextView tv_time;
        private final TextView tv_title;
        private final TextView tv_disease;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            simp_image = itemView.findViewById(R.id.be_adoption_item_image);
            tv_name = itemView.findViewById(R.id.be_adoption_item_name);
            tv_title = itemView.findViewById(R.id.be_adoption_item_title);
            tv_disease = itemView.findViewById(R.id.be_adoption_item_disease);
            tv_time = itemView.findViewById(R.id.be_adoption_item_time);
            tv_comment = itemView.findViewById(R.id.be_adoption_item_count);


        }
    }


}
