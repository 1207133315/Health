package com.wd.health.adapter.holder;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bw.health.bean.MationBean;
import com.bw.health.util.DateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.health.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
  public   ImageView delete;
    public HolderTwo(@NonNull View itemView) {
        super(itemView);
        img1=itemView.findViewById(R.id.img1);
        img2=itemView.findViewById(R.id.img2);
        img3=itemView.findViewById(R.id.img3);
        text=itemView.findViewById(R.id.text);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
        delete=itemView.findViewById(R.id.delete2);
    }

    @Override
    public void onBindView( Context context, MationBean mationBean) {
         String[] split = mationBean.thumbnail.split(";");
        Log.i("aaaa",""+split.length);

        img1.setImageURI(Uri.parse(split[0]));
        img2.setImageURI(Uri.parse(split[1]));
        img3.setImageURI(Uri.parse(split[2]));
        text.setText(mationBean.title);
        name.setText(mationBean.source);
         long time1 = mationBean.releaseTime;
        try {
             String s = DateUtils.dateTransformer(time1, null);
            time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String format = sdf.format(new Date(time1));*/
        itemView.setOnTouchListener(new View.OnTouchListener() {
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
                                delete.setVisibility(View.VISIBLE);
                            }else {
                                delete.setVisibility(View.GONE);
                            }
                        }else {
                            delete.setVisibility(View.GONE);
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
