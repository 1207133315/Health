package com.wd.health.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.interrogation.R;
import com.wd.health.bean.DoctordetailBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PingjiaAdapter extends BaseQuickAdapter<DoctordetailBean.CommentListBean,BaseViewHolder> {
    public PingjiaAdapter(int layoutResId, @Nullable List<DoctordetailBean.CommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctordetailBean.CommentListBean item) {
        helper.setText(R.id.name,item.getNickName());
        SimpleDraweeView view = helper.getView(R.id.head);
        view.setImageURI(item.getHeadPic());
        helper.setText(R.id.time,item.getCommentTime()+"");
        helper.setText(R.id.pingjia,item.getContent());
    }
}
