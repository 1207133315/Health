package com.wd.health.activity.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * com.wd.health.activity.adapter
 *
 * @author 李宁康
 * @date 2019 2019/08/02 20:08
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {


    public BaseViewHolder(View itemView) {
        super(itemView);
        findView(itemView);
    }

    public abstract void findView(View view);

    public abstract void setHolderData(Object o, int position);


}
