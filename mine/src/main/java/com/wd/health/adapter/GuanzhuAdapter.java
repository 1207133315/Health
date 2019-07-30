package com.wd.health.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.health.R;
import com.wd.health.bean.DoctorBean;

import java.util.List;

import androidx.annotation.Nullable;

public class GuanzhuAdapter extends BaseQuickAdapter<DoctorBean,BaseViewHolder> {

    public GuanzhuAdapter(int layoutResId, @Nullable List<DoctorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorBean item) {
        Glide.with(mContext).load(item.getImagePic()).into((ImageView) helper.getView(R.id.img));
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.zhiwu,item.getJobTitle());
        helper.setText(R.id.yiyuan,item.getInauguralHospital()+"/"+item.getDepartmentName());
        helper.setText(R.id.haopinglv,"好评率 "+item.getPraise());
        helper.setText(R.id.fuwu,"服务患者数 "+item.getPraiseNum());
        helper.getView(R.id.quxiao).setVisibility(View.GONE);
        helper.addOnClickListener(R.id.quxiao);
        helper.setOnTouchListener(R.id.aaa,new View.OnTouchListener() {
            private float moveX;
            private float x;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        if (x>moveX){
                            if (x-moveX>=60){
                                helper.getView(R.id.quxiao).setVisibility(View.VISIBLE);
                            }else {
                                helper.getView(R.id.quxiao).setVisibility(View.GONE);
                            }
                        }else {
                            helper.getView(R.id.quxiao).setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
}
