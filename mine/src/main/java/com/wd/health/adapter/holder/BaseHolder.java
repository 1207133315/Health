package com.wd.health.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bw.health.bean.MationBean;
import com.wd.health.mine.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * com.bw.health.adapter.holder
 *
 * @author 李宁康
 * @date 2019 2019/07/12 10:52
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {


    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        /*delete=itemView.findViewById(R.id.delete);*/

}
    public abstract void onBindView( Context context,MationBean mationBean);
}
