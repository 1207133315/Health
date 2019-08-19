package com.bw.health.adapter.holder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bw.health.shouye.R;
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
public class HolderOne extends BaseHolder {
    SimpleDraweeView img;
    TextView text;
    TextView name;
    TextView time;
    public HolderOne(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.img);
        text=itemView.findViewById(R.id.text);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
    }

    @Override
    public void onBindView( Context context, MationBean mationBean) {
        img.setImageURI(Uri.parse(mationBean.thumbnail));
        text.setText(mationBean.title);
        name.setText(mationBean.source);
         long time = mationBean.releaseTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String format = sdf.format(new Date(time));
        this.time.setText(format);
    }
}
