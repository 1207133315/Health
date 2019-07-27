package com.wd.health.adapter.holder;

import android.content.Context;
import android.net.Uri;
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
public class HolderOne extends BaseHolder {
    SimpleDraweeView img;
    TextView text;
    TextView name;
    TextView time;
   public  ImageView delete;
    public HolderOne(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.img);
        text=itemView.findViewById(R.id.text);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
        delete=itemView.findViewById(R.id.delete);
    }

    @Override
    public void onBindView( Context context, MationBean mationBean) {
        img.setImageURI(Uri.parse(mationBean.thumbnail));
        text.setText(mationBean.title);
        name.setText(mationBean.source);
         long time1 = mationBean.releaseTime;

        String s = null;
        try {
            s = DateUtils.dateTransformer(time1, null);
            time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
