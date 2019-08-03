package com.wd.health.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wd.health.R;

public class Myprogressbar extends RelativeLayout {

    private Paint paint;
    private int index;
    private Paint paint2;
    private ProgressBar bar;
    private Context context;
    public Myprogressbar(Context context) {
        this(context,null);
    }

    public Myprogressbar(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Myprogressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initpaint();
        this.context=context;
        bar = (ProgressBar) inflate(context, R.layout.progressbar, this).findViewById(R.id.bbb);
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (int i=0;i<7;i++){
            if (i>=index){
                canvas.drawCircle(24+51*3*i,24,24,paint2);
            } else{
                canvas.drawCircle(24+51*3*i,24,24,paint);
            }
            if (i==index){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.my_task_select);
                canvas.drawBitmap(bitmap,51*3*(i-1),0,paint);
            }
        }

        bar.setProgress((index-1)*2+1);
    }


    //初始化画笔
    public void initpaint(){
        paint = new Paint();
        paint.setColor(Color.parseColor("#3087ea"));

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);//设置抗锯齿效果 设置true的时边缘会将锯齿模糊化
        paint.setDither(true);//设置防抖动,设置true的时图片看上去会更柔和点
        paint.setStrokeWidth(10);
        paint2 = new Paint();
        paint2.setColor(Color.parseColor("#dddddd"));

        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);//设置抗锯齿效果 设置true的时边缘会将锯齿模糊化
        paint2.setDither(true);//设置防抖动,设置true的时图片看上去会更柔和点
        paint2.setStrokeWidth(10);
    }
    public void setindex(int index){
        this.index=index;
        invalidate();
    }
}
