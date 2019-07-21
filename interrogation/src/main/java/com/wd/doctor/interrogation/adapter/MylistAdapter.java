package com.wd.doctor.interrogation.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.doctor.interrogation.R;
import com.wd.doctor.interrogation.bean.DepartmentBean;


import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.Nullable;

public class MylistAdapter extends BaseQuickAdapter<DepartmentBean,BaseViewHolder> {

    public MylistAdapter(int layoutResId, @Nullable List<DepartmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentBean item) {
        helper.setText(R.id.text1,item.departmentName);
        TextView view = helper.getView(R.id.text1);
        if (item.isChecked){
            view.setTextColor(mContext.getResources().getColor(R.color.zi));
        }else {
            view.setTextColor(mContext.getResources().getColor(R.color.zii));
        }
    }
}
