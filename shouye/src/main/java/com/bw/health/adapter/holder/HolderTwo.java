package com.bw.health.adapter.holder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bw.health.R;
import com.bw.health.bean.MationBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * com.bw.health.adapter.holder
 *
 * @author 李宁康
 * @date 2019 2019/07/12 10:55
 */
public class HolderTwo extends BaseHolder {
    SimpleDraweeView img1;
    SimpleDraweeView img2;
    SimpleDraweeView img3;
    TextView text;
    TextView name;
    TextView time;
    public HolderTwo(@NonNull View itemView) {
        super(itemView);
        img1=itemView.findViewById(R.id.img1);
        img2=itemView.findViewById(R.id.img2);
        img3=itemView.findViewById(R.id.img3);
        text=itemView.findViewById(R.id.text);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
    }

    @Override
    public void onBindView( Context context, MationBean mationBean) {
        final String[] split = mationBean.thumbnail.split(";");


        img1.setImageURI(Uri.parse(split[0]));
        img2.setImageURI(Uri.parse(split[1]));
        img3.setImageURI(Uri.parse(split[2]));
        text.setText(mationBean.title);
        name.setText(mationBean.source);
         long time1 = mationBean.releaseTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String format = sdf.format(new Date(time1));
       time.setText(format);
    }
}
