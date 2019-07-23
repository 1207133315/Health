package com.bw.health.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.health.R;

/**
 * @author: 温壮
 * @Date: 2019/7/13 11:06
 * @description: $描述
 */
public class MyView extends ViewGroup {


    private FlowClick flowClick;
    private final static int MYLEFT=30;
    private final static int MYTOP=30;
    private int color;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context, attrs);
    }

//    private void init(Context context, AttributeSet attrs) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.flow);
//        color = array.getColor(R.styleable.flow_textColor, 0);
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        累加宽度变量
        int mywidth=MYLEFT;
//        设置行高变量
        int myheight=MYTOP;
        for (int i = 0; i <getChildCount() ; i++) {
//            获取每个元素
            TextView childAt = (TextView) getChildAt(i);
//            获取标签宽度
            int childAtWidth = childAt.getMeasuredWidth();
//            获取标签高度
            int childAtHeight = childAt.getMeasuredHeight();
//            如果间距+标签宽度+累计宽度>总宽度     换行
//            else     设置标签位置
            if (MYLEFT+childAtWidth+mywidth>getWidth()){
//                重置宽度变量
                mywidth=MYLEFT;
//                累计添加高度
                myheight+=MYTOP+childAtHeight;
//                mywidth+childAtWidth    添加标签的宽度位置
//                myheight+childAtHeight   添加标签的高度位置
                childAt.layout(mywidth,myheight,mywidth+childAtWidth,myheight+childAtHeight);
            }else{
                childAt.layout(mywidth,myheight,mywidth+childAtWidth,myheight+childAtHeight);
            }
//            标签的位置设置后  将标签宽度添加到宽度变量
            mywidth+=MYLEFT+childAtWidth;
        }
    }

    public void setFlowItemClick(FlowClick flowClick){
        this.flowClick=flowClick;
    }

    public interface FlowClick{
        void FlowItemClick(TextView view);
    }
//    public void closeView(){
//        removeAllViews();
//    }
}
