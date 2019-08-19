package com.wd.health.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wd.health.mine.R;

import androidx.annotation.Nullable;

public class NV extends View {

    private Paint paint;
    private Paint paint1;

    public NV(Context context) {
        this(context,null);
    }

    public NV(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置样式
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor("#999999"));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl, null);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        canvas.drawBitmap(bitmap,0,0,paint);
        RectF oval = new RectF(0,0,width,height);
        paint.setAlpha(179);
        canvas.drawArc(oval,40,100,false,paint);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.common_icon_collect_n, null);
        int height1 = bitmap2.getHeight();
        int width1 = bitmap2.getWidth();
        paint.setAlpha(255);
        canvas.drawBitmap(bitmap2,width/2-width1/2,height-height1-8,paint);
    }
}
