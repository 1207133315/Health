package com.wd.health.activity.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wd.health.activity.activity.IMActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;

/**
 * com.wd.health.activity.message
 *
 * @author 李宁康
 * @date 2019 2019/08/02 19:04
 */
public class GlobalEventListener {
    private Context MainContext;


    private static IMActivity JG_details = null;// 会话详情对象

    public GlobalEventListener(Context context) {
        MainContext = context;
        JMessageClient.registerEventReceiver(this);
    }
    public static void setJG(Activity activity, boolean islist) {



            JG_details = (IMActivity) activity;


    }
    //通知点击 前往会话列表
    public void onEvent(NotificationClickEvent event) {
        MainContext.startActivity(new Intent(MainContext, IMActivity.class));
    }

    // 接收消息 (主线程)(刷新UI)
    public void onEventMainThread(MessageEvent event){
        if (JG_details != null) {
            JG_details.initData();
        }
    }


}
