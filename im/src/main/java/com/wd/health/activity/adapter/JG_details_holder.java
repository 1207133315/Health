package com.wd.health.activity.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.health.bean.LoginBean;
import com.bw.health.util.GetDaoUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wd.health.R;

import androidx.annotation.RequiresApi;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.model.Message;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * com.wd.health.activity.adapter
 *
 * @author 李宁康
 * @date 2019 2019/08/02 20:10
 */
public class JG_details_holder extends BaseViewHolder implements View.OnClickListener {

    private RoundedImageView MyImg;  //发送的图片
    private TextView MyTv_content, MyTV_Time, My_tc, My_tc1, My_Tv_state;
    private CircleImageView MyHead;
    private Context MyContext;

    private JG_details_Adapter.OnItemClickListener mOnItemClickLis = null;
    private View view;

    public JG_details_holder(View itemView, Context con, JG_details_Adapter.OnItemClickListener mOnItemClick) {
        super(itemView);
        MyContext = con;
        mOnItemClickLis = mOnItemClick;


    }

    @Override
    public void findView(View view) {
        this.view = view;
        MyImg = this.view.findViewById(R.id.item_jg_details_img);//图片
        MyHead = view.findViewById(R.id.item_jg_details_head);  //头像

        MyTv_content = view.findViewById(R.id.item_jg_details_content);//内容
        MyTV_Time = view.findViewById(R.id.item_jg_details_time); // 时间
        My_tc = view.findViewById(R.id.item_jg_details_tc);
        My_tc1 = view.findViewById(R.id.item_jg_details_tc1);


        MyImg.setOnClickListener(this);
        MyHead.setOnClickListener(this);
        MyTv_content.setOnClickListener(this);
        MyTV_Time.setOnClickListener(this);


    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHolderData(Object o, int position) {
        if (o != null) {
            Message bean = (Message) o;
            final LoginBean loginBean = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll().get(0);

            if (bean.getFromUser() != null) {
                if (bean.getFromUser().getUserName().equals( loginBean.getUserName())) {
                    //是自己的聊天
                    MyHead = view.findViewById(R.id.item_jg_details_head1);  //头像 右边
                    MyHead.setVisibility(View.VISIBLE);//头像显示隐藏

                    view.findViewById(R.id.item_jg_details_head).setVisibility(View.GONE);
                    //内容背景
                    MyTv_content.setBackgroundResource(R.drawable.right);
                    MyTv_content.setTextColor(MyContext.getColor(R.color.black));
                    My_tc.setVisibility(View.VISIBLE);//权重挤压
                    My_tc1.setVisibility(View.GONE);



                } else {

                    MyHead = view.findViewById(R.id.item_jg_details_head);  //头像
                    MyHead.setVisibility(View.VISIBLE);//头像显示隐藏
                    view.findViewById(R.id.item_jg_details_head1).setVisibility(View.GONE);
                    MyHead.setImageResource(R.mipmap.doctor);
                    MyTv_content.setBackgroundResource(R.drawable.left);
                    MyTv_content.setTextColor(MyContext.getColor(R.color.black));
                    My_tc.setVisibility(View.GONE);
                    My_tc1.setVisibility(View.VISIBLE);

                }
                MyHead.setOnClickListener(this); //刷新头像点击事件
                //头像
                bean.getFromUser().getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        if (bitmap != null) {
                            MyHead.setImageBitmap(bitmap);
                        } else {
                            Log.e("极光会话详情-用户头像赋值", "bitmap为空!");
                        }

                    }
                });

                switch (bean.getContentType()) {
                    case text:
                        MyTv_content.setVisibility(View.VISIBLE);
                        MyTV_Time.setVisibility(View.GONE);
                        MyImg.setVisibility(View.GONE);
                        //内容
                        TextContent textContent = (TextContent) bean.getContent();
                        String text = textContent.getText();
                        MyTv_content.setText(text);
                        break;
                    case image:
                        MyTv_content.setVisibility(View.GONE);
                        MyTV_Time.setVisibility(View.GONE);
                        MyImg.setVisibility(View.VISIBLE);
                        ImageContent imageContent = (ImageContent) bean.getContent();
                        if (imageContent.getLocalThumbnailPath() != null) {
                            Glide.with(MyContext).load(imageContent.getLocalThumbnailPath()).into(MyImg);
                        }
                        break;
                    case prompt: //提示
                        MyTv_content.setVisibility(View.GONE);
                        MyTV_Time.setVisibility(View.VISIBLE);
                        MyImg.setVisibility(View.GONE);
                        //内容
                        PromptContent promptContent = (PromptContent) bean.getContent();
                        String promptText = promptContent.getPromptText();
                        MyTV_Time.setText(promptText);
                        break;
                    case voice:
                        VoiceContent voiceContent = (VoiceContent) bean.getContent();
                         String localPath = voiceContent.getLocalPath();
                        Log.i("voice", "setHolderData: "+localPath);
                        MyTv_content.setVisibility(View.VISIBLE);
                        MyTV_Time.setVisibility(View.GONE);
                        MyImg.setVisibility(View.GONE);
                        MyTv_content.setText(localPath);
                        break;
                }
            }


        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickLis != null) {
            mOnItemClickLis.onItemClick(v, getPosition());
        }
    }

}
