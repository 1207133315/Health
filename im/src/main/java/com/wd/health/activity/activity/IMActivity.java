package com.wd.health.activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bw.health.SoftKeyBoardListener;
import com.bw.health.bean.LoginBean;
import com.bw.health.bean.Result;
import com.bw.health.core.DataCall;
import com.bw.health.core.WDActivity;
import com.bw.health.exception.ApiException;
import com.bw.health.util.GetDaoUtil;
import com.bw.health.util.PermissionsUtils;
import com.bw.health.util.RsaCoder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wd.health.R;
import com.wd.health.activity.adapter.JG_details_Adapter;
import com.wd.health.activity.bean.MessageBean;
import com.wd.health.activity.bean.UserRecordBean;
import com.wd.health.activity.message.GlobalEventListener;
import com.wd.health.activity.presenter.FindMessagePresenter;
import com.wd.health.activity.presenter.NowWZPresenter;
import com.wd.health.activity.presenter.PushTextPresenter;
import com.wd.health.activity.view.AudioRecoderUtils;
import com.wd.health.activity.view.AudioRecorderButton;
import com.wd.health.activity.view.PressedView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@Route(path = "/IMActivity/")
public class IMActivity extends WDActivity implements View.OnClickListener {

    private NowWZPresenter nowWZPresenter;
    private PushTextPresenter pushTextPresenter;
    private LoginBean loginBean;
    private String userName1;
    private Conversation mConversation;
    private String userName;
    /**
     * 小美
     */
    private TextView name;
    private ImageView back;
    private RelativeLayout top;
    private ImageView voice;
    private EditText context;
    private ImageView expression;
    private ImageView send;
    RelativeLayout layout_main;
    RecyclerView recyclerView;
    private FindMessagePresenter findMessagePresenter;
    private JG_details_Adapter mAdapter;
    private String doctorName;
    private int height;
    RelativeLayout edit;
    private LinearLayoutManager linearLayoutManager;
    PressedView yuyin;
    private AudioRecoderUtils audioRecoderUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_im;
    }

    String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            //Toast.makeText(MessiageActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void forbitPermissons() {
//            finish();
            // Toast.makeText(MessiageActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
        }
    };
    public int longTime=0;
    @SuppressLint({"WrongViewCast", "WrongConstant"})
    @Override
    protected void initView() {
        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
        edit=findViewById(R.id.edit);
        yuyin=findViewById(R.id.yuyin);
        recyclerView=findViewById(R.id.recycler);
        layout_main=findViewById(R.id.layout_main);
        name = (TextView) findViewById(R.id.name);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        top = (RelativeLayout) findViewById(R.id.top);
        voice = (ImageView) findViewById(R.id.voice);
        voice.setOnClickListener(this);
        context = (EditText) findViewById(R.id.context);
        expression = (ImageView) findViewById(R.id.expression);
        expression.setOnClickListener(this);
        send = (ImageView) findViewById(R.id.send);
        send.setOnClickListener(this);
        userName1 = getSharedPreferences("aa",MODE_PRIVATE).getString("userName","aa");
        doctorName = getIntent().getExtras().getString("name");
        name.setText(doctorName);
        try {
            userName = RsaCoder.decryptByPublicKey(userName1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WindowManager manager =getWindowManager();
        height = manager.getDefaultDisplay().getHeight();
        nowWZPresenter = new NowWZPresenter(new NowWZ());
        pushTextPresenter = new PushTextPresenter(new PushText());
        findMessagePresenter = new FindMessagePresenter(new FindMessage());
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new JG_details_Adapter(this);
        recyclerView.setAdapter(mAdapter);

        audioRecoderUtils = new AudioRecoderUtils();
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.setVisibility(View.GONE);
                yuyin.setVisibility(View.VISIBLE);
            }
        });
        //录音按钮监听
        yuyin.setCallback(new PressedView.PressCallback() {

            @Override
            public void onStartRecord() {
                isFinish = false;
                audioRecoderUtils.startRecord();

            }

            @Override
            public void onStopRecord() {
                isFinish = true;
                audioRecoderUtils.stopRecord();
            }

            @Override
            public void onCancelRecord() {
                isFinish = true;
                audioRecoderUtils.cancelRecord();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height1) {
                //2 获取父控件的属性并且设置好属性
                RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 144);
                buttonLayoutParams.setMargins(0, height-height1-144, 0, 0);
                edit.setLayoutParams(buttonLayoutParams);
               /* RelativeLayout.LayoutParams recyclerLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                recyclerLayoutParams.setMargins(0, top.getLayoutParams().height+18, 0, height1+144);
                recyclerView.setLayoutParams(recyclerLayoutParams);
                recyclerView.smoothScrollToPosition(mConversation.getAllMessage().size() -1);*/
                //Toast.makeText(IMActivity.this, height+"-----"+height1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height1) {
                RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 144);
                buttonLayoutParams.setMargins(0, height-144, 0, 0);
                edit.setLayoutParams(buttonLayoutParams);
                RelativeLayout.LayoutParams recyclerLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                recyclerLayoutParams.setMargins(0, top.getLayoutParams().height+18, 0, 144);
                recyclerView.setLayoutParams(recyclerLayoutParams);
                //Toast.makeText(IMActivity.this, ""+height1, Toast.LENGTH_SHORT).show();
            }
        });
        final List<LoginBean> loginBeans = GetDaoUtil.getGetDaoUtil().getUserDao().loadAll();
        if (loginBeans.size() <= 0) {
            intentByRouter("/LoginActivity/");
        } else {

            //创建单聊会话
            mConversation = Conversation.createSingleConversation(userName, "b5f102cc307091e167ce52e0");
            //进入单聊会话
            JMessageClient.enterSingleConversation(userName, "b5f102cc307091e167ce52e0");
            //设置消息接收 监听
            //GlobalEventListener.setJG(this, false);
            JMessageClient.registerEventReceiver(this);
            //JMessageClient.enterSingleConversation(this.userName);
            loginBean = loginBeans.get(0);
            nowWZPresenter.reqeust(loginBean.getId(), loginBean.getSessionId());
            initData();

        }
    }


    public class FindMessage implements DataCall<Result<List<MessageBean>>>{
        @Override
        public void success(Result<List<MessageBean>> data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.voice) {
        } else if (i == R.id.expression) {
        } else if (i == R.id.send) {
        } else {
        }
    }


    public class NowWZ implements DataCall<Result<UserRecordBean>> {
        @Override
        public void success(Result<UserRecordBean> data, Object... args) {
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context.getText().toString().length()>0&&!context.getText().toString().equals("")){
                    Message message = mConversation.createSendMessage(new TextContent(context.getText().toString()));
                        message.setOnSendCompleteCallback(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                  //  Toast.makeText(IMActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                    initData();

                                } else {
                                    Toast.makeText(IMActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    JMessageClient.sendMessage(message);
                   // pushTextPresenter.reqeust(loginBean.getId(),loginBean.getSessionId(),data.getResult().recordId,context.getText(),1,data.getResult().doctorId);
                    }else {
                        Snackbar.make(layout_main,"内容不能为空！",BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            });
            audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
                @Override
                public void onUpdate(double db, long time) {
                    yuyin.setVolume(3000 + 6000 * db / 100);
                    longTime=(int)time;
                }

                @Override
                public void onStop(String filePath) {
                    Toast.makeText(IMActivity.this, ""+filePath, Toast.LENGTH_SHORT).show();
                    try {
                         Message voiceMessage = mConversation.createSendMessage(new VoiceContent(new File(filePath),longTime));

                        JMessageClient.sendMessage(voiceMessage);
                        voiceMessage.setOnSendCompleteCallback(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i==0){
                                    Toast.makeText(IMActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                    initData();
                                }else {
                                    Toast.makeText(IMActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    public class PushText implements DataCall<Result> {
        @Override
        public void success(Result data, Object... args) {

        }

        @Override
        public void fail(ApiException data, Object... args) {

        }
    }

    @Override
    protected void destoryData() {

    }
    boolean isFinish = false;
    /**
     * 模拟音量线程
     */
   /* private class TestThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isFinish) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           *//* //模拟音量
                            int volume = (int) (Math.random() * 30000);
                            yuyin.setVolume(volume);*//*



                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/
    public void onEvent(MessageEvent event) {
        //获取事件发生的会话对象
        //Conversation conversation = event.getConversation();
        Message newMessage = event.getMessage();//获取此次离线期间会话收到的新消息列表
        initData();
       // System.out.println(String.format(Locale.SIMPLIFIED_CHINESE, "收到一条来自%s的在线消息。\n", conversation.getTargetId()));
    }
    private boolean one;
    public void initData() {

        if (mConversation != null) {
          /* title.setText(mConversation.getTitle() == null ? "" : mConversation.getTitle());*/
            UserInfo info = (UserInfo) mConversation.getTargetInfo();
            userName = info.getUserName();
            //userName = "f8443445-a7ef-47d8-8005-b0d57851b396";  //todo 可自定义
            //使列表滚动到底部
            if (mConversation.getAllMessage() != null) {
                if (mConversation.getAllMessage().size() > 0) {
                    final List<Message> allMessage = mConversation.getAllMessage();
                    mAdapter.setData(allMessage);
                    //设置刷新不闪屏
                    ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    if (one) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.notifyItemInserted(mConversation.getAllMessage().size() - 1);

                    }
                   // recyclerView.smoothScrollToPosition(mConversation.getAllMessage().size());
                    linearLayoutManager.scrollToPositionWithOffset(mConversation.getAllMessage().size()-1, 0);//先要滚动到这个位置
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            View target = linearLayoutManager.findViewByPosition(mConversation.getAllMessage().size()-1);//然后才能拿到这个View
                            if (target != null) {
                                linearLayoutManager.scrollToPositionWithOffset(mConversation.getAllMessage().size()-1,
                                        recyclerView.getMeasuredHeight() - target.getMeasuredHeight());//滚动偏移到底部
                            }
                        }
                    });
                }
            }



        }
        one=false; // 代表不是第一次initData
        context.setText("");
    }

    @Override
    protected void onDestroy() {
        JMessageClient.exitConversation();
        GlobalEventListener.setJG(null,false);
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
