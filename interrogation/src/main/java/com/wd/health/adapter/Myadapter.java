package com.wd.health.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.interrogation.R;
import com.wd.health.bean.Doctor;

import java.util.List;

import androidx.annotation.Nullable;

public class Myadapter extends BaseQuickAdapter<Doctor,BaseViewHolder> {
    public Myadapter(int layoutResId, @Nullable List<Doctor> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Doctor item) {
        helper.setText(R.id.tt,item.getDoctorName());
        SimpleDraweeView view = helper.getView(R.id.img);
        if (item.getImagePic()!=null&&item.getImagePic()!=""){
            view.setImageURI(item.getImagePic());
        }else {
            view.setImageResource(R.drawable.aaa);
        }
        TextView text = helper.getView(R.id.tt);
        if (item.isSelect()){
            text.setBackgroundResource(R.drawable.selectedtext);
        }else {
            text.setBackgroundResource(R.drawable.noselectedtext);
        }
    }
}
